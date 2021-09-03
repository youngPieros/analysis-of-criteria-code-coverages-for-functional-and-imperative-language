{-# LANGUAGE DuplicateRecordFields #-}

module Application
( Application.addCourse
, Application.addStudent
, Application.getCourses
, Application.getCourse
, Application.addToWeeklySchedule
, Application.removeFromWeeklySchedule
, Application.getStudentWeeklySchedule
, Application.finalizeWeeklySchedule
) where


import Data.List
import Data.Function
import Data.Maybe

import DataBase
import Response
import CommandArguments
import DataMapper
import Course
import Student
import DTO
import TermCourseStatus
import TermCourse
import StudentScheduleCourse
import ExamTime
import ClassTime
import Util

addCourse :: DataBase -> AddCourseArgument -> (DataBase, Response)
addCourse db args
    | searchedCourse == course = (db, Response "This course has conflict in class time with current courses")
    | otherwise = (DataBase.addCourse db course, Response "successful")
    where
        searchedCourse = searchCourse db ((Course.code :: Course -> String) course)
        course = toCourse args


addStudent :: DataBase -> AddStudentArgument -> (DataBase, Response)
addStudent db args
    | searchedStudent == NullStudent = (DataBase.addStudent db student, Response "Student is successfully registered")
    | otherwise = (db, Response "There is a student with this studentId")
    where
        searchedStudent = DataBase.findStudent db ((studentId :: AddStudentArgument -> String) args)
        student = toStudent args


getCourses :: DataBase -> GetCoursesArgument -> (DataBase, Response)
getCourses db args
    | student == NullStudent = (db, Response "StudentNotFound")
    | otherwise = (db, Response courses)
    where
        courses = toDTO $ map (toCourseSummary) (Data.List.sort (DataBase.getCourses db))
        student = findStudent db ((studentId :: GetCoursesArgument -> String) args)


getCourse :: DataBase -> GetCourseArgument -> (DataBase, Response)
getCourse db args
    | student == NullStudent = (db, Response "StudentNotFound")
    | course == NullCourse = (db, Response "OfferingNotFound")
    | otherwise = (db, Response (toDTO course))
    where
        course = searchCourse db ((code :: GetCourseArgument -> String) args)
        student = DataBase.findStudent db ((studentId :: GetCourseArgument -> String) args)


addToWeeklySchedule :: DataBase -> AddToWeeklyScheduleArgument -> (DataBase, Response)
addToWeeklySchedule db (AddToWeeklyScheduleArgument sid courseCode)
    | student == NullStudent = (db, Response "StudentNotFound")
    | course == NullCourse = (db, Response "OfferingNotFound")
    | scheduleCourses == NullSchedule = (upsertStudentScheduleCourses db sid (addTermCourse (createStudentScheduleCourse sid) termCourse), Response "")
    | elem termCourse (termCourses scheduleCourses) = (db, Response "RepetitiveCourseError")
    | otherwise = (upsertStudentScheduleCourses db sid (addTermCourse scheduleCourses termCourse), Response "")
    where
        scheduleCourses = DataBase.findStudentScheduleCourses db sid
        termCourse = toTermCourse course
        course = searchCourse db courseCode
        student = DataBase.findStudent db sid

removeFromWeeklySchedule :: DataBase -> RemoveFromWeeklyScheduleArgument -> (DataBase, Response)
removeFromWeeklySchedule db (RemoveFromWeeklyScheduleArgument sid courseCode)
    | student == NullStudent = (db, Response "StudentNotFound")
    | course == NullCourse = (db, Response "OfferingNotFound")
    | courses == [] = (db, Response "User did not schedule this course")
    | otherwise = (upsertStudentScheduleCourses db sid (removeTermCourse scheduleCourses courseCode), Response "")
    where
        courses = getTermCourses scheduleCourses
        scheduleCourses = DataBase.findStudentScheduleCourses db sid
        course = searchCourse db courseCode
        student = DataBase.findStudent db sid

getStudentWeeklySchedule :: DataBase -> GetWeeklyScheduleArgument -> (DataBase, Response)
getStudentWeeklySchedule db (GetWeeklyScheduleArgument sid)
    | student == NullStudent = (db, Response "StudentNotFound")
    | otherwise = (db, Response (toDTO courses))
    where
        courses = getTermCourses scheduleCourses
        scheduleCourses = DataBase.findStudentScheduleCourses db sid
        student = DataBase.findStudent db sid

getCoursesCapacities :: DataBase -> [String] -> [(String, Int, Int)]
getCoursesCapacities db coursesCodes = result
    where
        result = map (\(c, s) -> (c, s, getCapacity (fromJust (find (\co -> (\c -> (code :: Course -> String) c) co == c) courses)))) coursesCapacities
        coursesCapacities = map (\g -> (getCode $ head g, length g)) groupedScheduleCourses
        groupedScheduleCourses = groupBy (\c1 c2 -> getCode c1 == getCode c2) (sortBy (compare `on` getCode) finalizedScheduledCourses)
        finalizedScheduledCourses = filter (\(TermCourse c _ _ _ _ s) -> (elem c coursesCodes) && (s == Finalized)) allScheduleCourses
        allScheduleCourses = concat $ map (\sc -> termCourses sc) (studentsScheduleCourses db)
        courses = map (searchCourse db) coursesCodes
        getCapacity = (\c -> (capacity :: Course -> Int) c)
        getCode = (\c -> (code :: TermCourse -> String) c)


finalizeWeeklySchedule :: DataBase -> FinalizeScheduleArgument -> (DataBase, Response)
finalizeWeeklySchedule db (FinalizeScheduleArgument sid)
    | student == NullStudent = (db, Response "StudentNotFound")
    | studentTermCourses == [] || nonFinalizedCourses == [] = (db, Response "Student did not add new course to his schedule courses")
    | unitsCourses < minimumUnits = (db, Response "MinimumUnitsError")
    | unitsCourses > maximumUnits = (db, Response "MaximumUnitsError")
    | isJust conflictOnClassTime = (db, Response ("ClassTimeCollisionError " ++ (codesString (fromJust conflictOnClassTime))))
    | isJust conflictOnExamTime = (db, Response ("ExamTimeCollisionError " ++ (codesString (fromJust conflictOnExamTime))))
    | isJust courseWithFullCapacity = (db, Response (getCapacityError (fromJust courseWithFullCapacity)))
    | otherwise = (upsertStudentScheduleCourses db sid (finalizeCourses scheduleCourses), Response "")
    where
        conflictOnClassTime = find (\(c1, c2) -> ClassTime.conflict (getClassTime c1) (getClassTime c2)) (toOrderedPais courses)
        conflictOnExamTime = find (\(c1, c2) -> ExamTime.conflict (getExamTime c1) (getExamTime c2)) (toOrderedPais courses)
        courseWithFullCapacity = find (\(co, ca, ma) -> ca >= ma) coursesCapacities
        coursesCapacities = getCoursesCapacities db nonFinalizedCodeCourses
        unitsCourses = sum $ map (\c -> (units :: Course -> Int) c) courses
        courses = map (searchCourse db) coursesCodes
        nonFinalizedCodeCourses = map (\c -> getCode c) nonFinalizedCourses
        nonFinalizedCourses = filter (\c -> status c == NonFinalized) studentTermCourses
        coursesCodes = map (\c -> getCode c) studentTermCourses
        studentTermCourses = getTermCourses scheduleCourses
        scheduleCourses = DataBase.findStudentScheduleCourses db sid
        student = DataBase.findStudent db sid
        minimumUnits = 12
        maximumUnits = 20
        getCapacityError = (\(c, _, _) -> "CapacityError " ++ c)
        codesString = (\(c1, c2) -> (getCourseCode c1) ++ " " ++ (getCourseCode c2))
        getCode = (\c -> (code :: TermCourse -> String) c)
        getCourseCode = (\c -> (code :: Course -> String) c)
        getExamTime = (\c -> (examTime :: Course -> ExamTime) c)
        getClassTime = (\c -> (classTime :: Course -> ClassTime) c)

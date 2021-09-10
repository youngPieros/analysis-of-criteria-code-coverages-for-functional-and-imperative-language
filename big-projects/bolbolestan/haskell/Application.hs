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
import DataAccess
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
    | searchedCourse == course = (db, ConflictOnAddCourse)
    | otherwise = (DataAccess.addCourse db course, SuccessfulEmptyResponse)
    where
        searchedCourse = searchCourse db ((Course.code :: Course -> String) course)
        course = toCourse args


addStudent :: DataBase -> AddStudentArgument -> (DataBase, Response)
addStudent db args
    | searchedStudent == NullStudent = (DataAccess.addStudent db student, SuccessfulEmptyResponse)
    | otherwise = (db, RepetitiveStudentInsertion)
    where
        searchedStudent = DataAccess.findStudent db ((studentId :: AddStudentArgument -> String) args)
        student = toStudent args


getCourses :: DataBase -> GetCoursesArgument -> (DataBase, Response)
getCourses db args
    | student == NullStudent = (db, StudentNotFound)
    | otherwise = (db, SuccessResponse courses)
    where
        courses = toDTO $ map (toCourseSummary) (DataAccess.getCourses db)
        student = findStudent db ((studentId :: GetCoursesArgument -> String) args)


getCourse :: DataBase -> GetCourseArgument -> (DataBase, Response)
getCourse db args
    | student == NullStudent = (db, StudentNotFound)
    | course == NullCourse = (db, OfferingNotFound)
    | otherwise = (db, SuccessResponse (toDTO course))
    where
        course = searchCourse db ((code :: GetCourseArgument -> String) args)
        student = DataAccess.findStudent db ((studentId :: GetCourseArgument -> String) args)


addToWeeklySchedule :: DataBase -> AddToWeeklyScheduleArgument -> (DataBase, Response)
addToWeeklySchedule db (AddToWeeklyScheduleArgument sid courseCode)
    | student == NullStudent = (db, StudentNotFound)
    | course == NullCourse = (db, OfferingNotFound)
    | scheduleCourses == NullSchedule = (upsertStudentScheduleCourses db sid (addTermCourse (createStudentScheduleCourse sid) termCourse), SuccessfulEmptyResponse)
    | elem termCourse (termCourses scheduleCourses) = (db, RepetitiveCourseError)
    | otherwise = (upsertStudentScheduleCourses db sid (addTermCourse scheduleCourses termCourse), SuccessfulEmptyResponse)
    where
        scheduleCourses = DataAccess.findStudentScheduleCourses db sid
        termCourse = toTermCourse course
        course = searchCourse db courseCode
        student = DataAccess.findStudent db sid

removeFromWeeklySchedule :: DataBase -> RemoveFromWeeklyScheduleArgument -> (DataBase, Response)
removeFromWeeklySchedule db (RemoveFromWeeklyScheduleArgument sid courseCode)
    | student == NullStudent = (db, StudentNotFound)
    | course == NullCourse = (db, OfferingNotFound)
    | courses == [] = (db, EmptySchedule)
    | otherwise = (upsertStudentScheduleCourses db sid (removeTermCourse scheduleCourses courseCode), SuccessfulEmptyResponse)
    where
        courses = getTermCourses scheduleCourses
        scheduleCourses = DataAccess.findStudentScheduleCourses db sid
        course = searchCourse db courseCode
        student = DataAccess.findStudent db sid

getStudentWeeklySchedule :: DataBase -> GetWeeklyScheduleArgument -> (DataBase, Response)
getStudentWeeklySchedule db (GetWeeklyScheduleArgument sid)
    | student == NullStudent = (db, StudentNotFound)
    | otherwise = (db, SuccessResponse (toDTO courses))
    where
        courses = getTermCourses scheduleCourses
        scheduleCourses = DataAccess.findStudentScheduleCourses db sid
        student = DataAccess.findStudent db sid

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
    | student == NullStudent = (db, StudentNotFound)
    | studentTermCourses == [] || nonFinalizedCourses == [] = (db, EmptySchedule)
    | unitsCourses < minimumUnits = (db, MinimumUnitsError)
    | unitsCourses > maximumUnits = (db, MaximumUnitsError)
    | isJust conflictOnClassTime = (db, ClassTimeCollisionError (fst $ fromJ conflictOnClassTime) (snd $ fromJ conflictOnClassTime))
    | isJust conflictOnExamTime = (db, ExamTimeCollisionError (fst $ fromJ conflictOnExamTime) (snd $ fromJ conflictOnExamTime))
    | isJust courseWithFullCapacity = (db, FullCapacityError ((\(c, _, _) -> c) (fromJust courseWithFullCapacity)))
    | otherwise = (upsertStudentScheduleCourses db sid (finalizeCourses scheduleCourses), SuccessfulEmptyResponse)
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
        scheduleCourses = DataAccess.findStudentScheduleCourses db sid
        student = DataAccess.findStudent db sid
        minimumUnits = 12
        maximumUnits = 20
        fromJ = (\x -> (\(c1, c2) -> (getCourseCode c1, getCourseCode c2)) (fromJust x))
        getCode = (\c -> (code :: TermCourse -> String) c)
        getCourseCode = (\c -> (code :: Course -> String) c)
        getExamTime = (\c -> (examTime :: Course -> ExamTime) c)
        getClassTime = (\c -> (classTime :: Course -> ClassTime) c)

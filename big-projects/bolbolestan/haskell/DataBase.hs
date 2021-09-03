{-# LANGUAGE DuplicateRecordFields #-}

module DataBase
( DataBase(..)
, getEmptyDataBase
, addCourse
, getCourses
, searchCourse
, addStudent
, findStudent
, findStudentScheduleCourses
, upsertStudentScheduleCourses
) where


import Data.List
import Data.Maybe

import Course
import Student
import StudentScheduleCourse



data DataBase = DataBase { courses :: [Course]
                         , students :: [Student]
                         , studentsScheduleCourses :: [StudentScheduleCourse]
                         }



getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{courses=[], students=[], studentsScheduleCourses=[]}

addCourse :: DataBase -> Course -> DataBase
addCourse db course = DataBase{courses=(course:(courses db)), students=(students db), studentsScheduleCourses=(studentsScheduleCourses db)}

searchCourse :: DataBase -> String -> Course
searchCourse db courseCode
    | Data.Maybe.isJust course = Data.Maybe.fromJust course
    | otherwise = NullCourse
    where
        course = Data.List.find (\c -> (code :: Course -> String) c == courseCode) (courses db)

getCourses :: DataBase -> [Course]
getCourses db = courses db

addStudent :: DataBase -> Student -> DataBase
addStudent db student = DataBase{courses=(courses db), students=(student:(students db)), studentsScheduleCourses=(studentsScheduleCourses db)}

findStudent :: DataBase -> String -> Student
findStudent db sid
    | isJust student = fromJust student
    | otherwise = NullStudent
    where
        student = Data.List.find (\s -> (studentId :: Student -> String) s == sid) (students db)


findStudentScheduleCourses :: DataBase -> String -> StudentScheduleCourse
findStudentScheduleCourses db sid
    | isJust courses = fromJust courses
    | otherwise = NullSchedule
    where
        courses = Data.List.find (\sc -> (studentId :: StudentScheduleCourse -> String) sc == sid) (studentsScheduleCourses db)

upsertStudentScheduleCourses :: DataBase -> String -> StudentScheduleCourse -> DataBase
upsertStudentScheduleCourses db sid sc = DataBase{courses=(courses db), students=(students db), studentsScheduleCourses=scheduledCourses}
    where
        scheduledCourses = sc:(filter (\c -> (studentId :: StudentScheduleCourse -> String) c /= sid) (studentsScheduleCourses db))

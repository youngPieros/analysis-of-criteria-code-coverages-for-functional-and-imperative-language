{-# LANGUAGE DuplicateRecordFields #-}

module Application
( Application.addCourse
, Application.addStudent
, Application.getCourses
, Application.getCourse
) where


import Data.List

import DataBase
import Response
import CommandArguments
import DataMapper
import Course
import Student
import DTO



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

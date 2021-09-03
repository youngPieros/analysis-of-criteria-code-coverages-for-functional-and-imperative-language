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
    | sameCourse == False = (DataBase.addCourse db course, Response "successful")
    | otherwise = (db, Response "This course has conflict in class time with current courses")
    where
        sameCourse = elem course searchedCourses
        searchedCourses = searchCourse db ((Course.code :: Course -> String) course)
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
    | courses == [] = (db, Response "OfferingNotFound")
    | otherwise = (db, Response (toDTO courses))
    where
        courses = searchCourse db ((code :: GetCourseArgument -> String) args)
        student = DataBase.findStudent db ((studentId :: GetCourseArgument -> String) args)

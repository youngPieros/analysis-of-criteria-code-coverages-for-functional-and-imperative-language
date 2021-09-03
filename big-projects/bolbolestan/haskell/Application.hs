{-# LANGUAGE DuplicateRecordFields #-}

module Application
( Application.addCourse
, Application.addStudent
) where


import DataBase
import Response
import CommandArguments
import DataMapper
import Course
import Student



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
module Application
( Application.addCourse
) where


import DataBase
import Response
import CommandArguments
import DataMapper
import Course



addCourse :: DataBase -> AddCourseArgument -> (DataBase, Response)
addCourse db args
    | sameCourse == False = (DataBase.addCourse db course, Response "successful")
    | otherwise = (db, Response "This course has conflict in class time with current courses")
    where
        sameCourse = elem course searchedCourses
        searchedCourses = searchCourse db ((Course.code :: Course -> String) course)
        course = toCourse args


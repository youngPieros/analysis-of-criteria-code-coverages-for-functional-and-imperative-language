{-# LANGUAGE DuplicateRecordFields #-}

module DataBase
( DataBase(..)
, getEmptyDataBase
) where


import Course
import Student
import StudentScheduleCourse



data DataBase = DataBase { courses :: [Course]
                         , students :: [Student]
                         , studentsScheduleCourses :: [StudentScheduleCourse]
                         }



getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{courses=[], students=[], studentsScheduleCourses=[]}


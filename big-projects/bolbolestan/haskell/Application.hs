module Application
( Application.addCourse
) where


import DataBase
import Response
import CommandArguments

addCourse :: DataBase -> AddCourseArgument -> (DataBase, Response)
addCourse db args = (db, Response "hiihihihihi")


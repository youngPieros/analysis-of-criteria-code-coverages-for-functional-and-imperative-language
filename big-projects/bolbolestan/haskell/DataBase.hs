module DataBase
( DataBase(..)
, getEmptyDataBase
, addCourse
, searchCourse
, addStudent
, findStudent
) where


import Data.List
import Data.Maybe

import Course
import Student


data DataBase = DataBase { courses :: [Course]
                         , students :: [Student]
                         }



getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{courses=[], students=[]}

addCourse :: DataBase -> Course -> DataBase
addCourse db course = DataBase{courses=(course:(courses db)), students=(students db)}

searchCourse :: DataBase -> String -> [Course]
searchCourse db courseCode = searchedCourses
    where
        searchedCourses = Data.List.filter (\c -> (code :: Course -> String) c == courseCode) (courses db)

addStudent :: DataBase -> Student -> DataBase
addStudent db student = DataBase{courses=(courses db), students=(student:(students db))}

findStudent :: DataBase -> String -> Student
findStudent db sid
    | isJust student = fromJust student
    | otherwise = NullStudent
    where
        student = Data.List.find (\s -> (studentId :: Student -> String) s == sid) (students db)



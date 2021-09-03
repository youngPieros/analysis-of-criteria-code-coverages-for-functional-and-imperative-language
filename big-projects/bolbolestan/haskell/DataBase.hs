module DataBase
( DataBase(..)
, getEmptyDataBase
, addCourse
, searchCourse
) where


import Data.List
import Data.Maybe

import Course



data DataBase = DataBase { courses :: [Course]
                         }



getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{courses=[]}

addCourse :: DataBase -> Course -> DataBase
addCourse db course = DataBase{courses=(course:(courses db))}

searchCourse :: DataBase -> String -> [Course]
searchCourse db courseCode = searchedCourses
    where
        searchedCourses = Data.List.filter (\c -> (code :: Course -> String) c == courseCode) (courses db)


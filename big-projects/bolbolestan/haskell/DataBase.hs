module DataBase
( DataBase(..)
, getEmptyDataBase
, addCourse
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


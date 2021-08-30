module DataBase
( DataBase(..)
, getEmptyDataBase
) where

import Restaurant
import Order



data DataBase = DataBase{ restaurants :: [Restaurant]
                        , orders :: [Order]
                        }

getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{restaurants=[], DataBase.orders=[]}

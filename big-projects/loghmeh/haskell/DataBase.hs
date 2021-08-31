module DataBase
( DataBase(..)
, getEmptyDataBase
, getRestaurant
, findFood
, getUserBasket
, updateOrder
, removeOrder
) where


import Data.List
import Data.Maybe

import Restaurant
import Food
import Order



data DataBase = DataBase{ restaurants :: [Restaurant]
                        , orders :: [Order]
                        } deriving (Show)



getEmptyDataBase :: DataBase
getEmptyDataBase = DataBase{restaurants=[], DataBase.orders=[]}

getRestaurant :: DataBase -> String -> Restaurant
getRestaurant db restaurantName
    | Data.Maybe.isJust restaurant = Data.Maybe.fromJust restaurant
    | otherwise = EmptyRestaurant
    where
        restaurant = Data.List.find (\restaurant -> (Restaurant.name :: Restaurant -> String) restaurant == restaurantName) (restaurants db)

findFood :: DataBase -> String -> String -> Food
findFood db restaurantName foodName = getFood restaurant foodName
    where
        restaurant = getRestaurant db restaurantName

getUserBasket :: DataBase -> String -> Order
getUserBasket db username
    | Data.Maybe.isJust basket = Data.Maybe.fromJust basket
    | otherwise = EmptyOrder
    where
        basket = Data.List.find (\b -> (user :: Order -> String) b == username) (orders db)

updateOrder :: DataBase -> Order -> DataBase
updateOrder db order = DataBase{restaurants=(restaurants db), orders=updatedOrders}
    where
        updatedOrders = order:(filter (\o -> o /= order) (orders db))

removeOrder :: DataBase -> Order -> DataBase
removeOrder db order = DataBase{restaurants=(restaurants db), orders=updatedOrders}
    where
        updatedOrders = filter (\o -> o /= order) (orders db)

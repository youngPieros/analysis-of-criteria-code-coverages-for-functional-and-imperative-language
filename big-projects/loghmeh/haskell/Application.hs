{-# LANGUAGE DuplicateRecordFields #-}

module Application
( addRestaurant
, addFood
, getRestaurants
, Application.getRestaurant
, Application.getFood
, addToCart
) where

import qualified Data.ByteString.Lazy.Char8 as C
import Data.List
import Data.Aeson
import Data.Maybe
import Food
import Order
import Location
import DataBase
import Response
import CommandArgument
import Restaurant



findAndDo :: (a -> b) -> (a -> Bool) -> [a] -> ([b], [a])
findAndDo f p list = (map f (filter p list), filter (\x -> not(p x)) list)


locationMapper :: LocationArgs -> Location
locationMapper location = Location{x = xPos, y = yPos}
    where
        yPos = (y :: LocationArgs -> Int) (location :: LocationArgs)
        xPos = (x :: LocationArgs -> Int) (location :: LocationArgs)

restaurantFoodMapper :: RestaurantFoodArgs -> Food
restaurantFoodMapper food = Food{name=foodName, description=foodDescription, popularity=foodPopularity, price=foodPrice}
    where
        foodName = (name :: RestaurantFoodArgs -> String) food
        foodPopularity = (popularity :: RestaurantFoodArgs -> Double) food
        foodPrice = (price :: RestaurantFoodArgs -> Double) food
        foodDescription = (description :: RestaurantFoodArgs -> String) food

foodMapper :: AddFoodArgs -> Food
foodMapper food = Food{name=foodName, description=foodDescription, popularity=foodPopularity, price=foodPrice}
    where
        foodName = (name :: AddFoodArgs -> String) food
        foodDescription = (description :: AddFoodArgs -> String) food
        foodPopularity = (popularity :: AddFoodArgs -> Double) food
        foodPrice = (price :: AddFoodArgs -> Double) food



addRestaurant :: Command -> DataBase -> (DataBase, Response)
addRestaurant (AddRestaurant (AddRestaurantArgs name description location menu)) db = (database, Response response)
    where
        response = if isRestaurantAlreadyInDB then ("there is " ++ name ++ " already in restaurants") else ""
        database = if isRestaurantAlreadyInDB then db else DataBase{restaurants=(restaurant:restaurants db), orders=(orders db)}
        isRestaurantAlreadyInDB = elem restaurant (restaurants db)
        restaurant = Restaurant{name=name, description=description, location=restaurantLocation, menu=restaurantMenu}
        restaurantMenu = map restaurantFoodMapper menu
        restaurantLocation = locationMapper location


addFoodToRestaurant :: Restaurant -> Food  -> (Restaurant, Response)
addFoodToRestaurant restaurant food = (rest, Response response)
    where
        rest = Restaurant{name=restaurantName, description=restaurantDescription, location=restaurantLocation, menu=newMenu}
        restaurantName = (name :: Restaurant -> String) restaurant
        restaurantDescription = (description :: Restaurant -> String) restaurant
        restaurantLocation = (location :: Restaurant -> Location) restaurant
        response = if isFoodAlreadyInRestaurant then ("there is " ++ ((name :: Food -> String) food) ++ " already in " ++ ((name :: Restaurant -> String) restaurant)) else ""
        newMenu = ((menu :: Restaurant -> [Food])restaurant) ++ (if isFoodAlreadyInRestaurant then [] else [food])
        isFoodAlreadyInRestaurant = elem food ((menu :: Restaurant -> [Food])restaurant)
addFood :: Command -> DataBase -> (DataBase, Response)
addFood (AddFood args) db = (database, response)
    where
        response = if length updated > 0 then snd (updated !! 0) else Response ("there is not " ++ restaurant ++ " restaurant")
        database = DataBase{restaurants=(rests ++ (map (fst) updated)), orders=(orders db)}
        (updated, rests) = findAndDo ((flip addFoodToRestaurant) food) (\res -> restaurant == ((name :: Restaurant -> String) res)) (restaurants db)
        food = foodMapper args
        restaurant = (restaurantName :: AddFoodArgs -> String) args


getRestaurants :: Command -> DataBase -> (DataBase, Response)
getRestaurants GetRestaurants database = (database, Response response)
    where
        response = unwords $ restaurantNames
        restaurantNames = map (\restaurant -> (name :: Restaurant -> String) restaurant) (restaurants database)


getRestaurant :: Command -> DataBase -> (DataBase, Response)
getRestaurant (GetRestaurant args) database = (database, Response response)
    where
        response = if Data.Maybe.isJust restaurant then (C.unpack . (encode :: Restaurant -> C.ByteString) $ (Data.Maybe.fromJust restaurant)) else ("there is not " ++ restaurantName ++ " restaurant")
        restaurant = Data.List.find (\rest -> (name :: Restaurant -> String) rest == restaurantName) (restaurants database)
        restaurantName = (name :: GetRestaurantArgs -> String) args


getFood :: Command -> DataBase -> (DataBase, Response)
getFood (GetFood args) database = (database, Response response)
    where
        response = if isFoodFound then C.unpack . (encode :: Food -> C.ByteString) $ (Data.Maybe.fromJust food) else "there is not this food"
        isFoodFound = Data.Maybe.isJust food
        food = Data.List.find (\f -> (name :: Food -> String) f == fName) restaurantMenu
        restaurantMenu = if isRestaurantFound then (menu :: Restaurant -> [Food]) (Data.Maybe.fromJust restaurant) else []
        isRestaurantFound = Data.Maybe.isJust restaurant
        restaurant = Data.List.find (\rest -> (name :: Restaurant -> String) rest == restName) (restaurants database)
        fName = (foodName :: GetFoodArgs -> String) args
        restName = (restaurantName :: GetFoodArgs -> String) args


addToCart :: Command -> DataBase -> (DataBase, Response)
addToCart (AddToCart args) db
    | basket /= EmptyOrder && (restaurantName :: Order -> String) basket /= restName = (db, Response "you can not order food from different restaurants")
    | food == EmptyFood = (db, Response "there is not this order")
    | basket == EmptyOrder = (updateOrder db (initOrder defaultUser restName fName 1), Response "")
    | otherwise = (updateOrder db (addOrder fName 1 basket), Response "")
    where
        basket = getUserBasket db defaultUser
        food = findFood db restName fName
        fName = (foodName :: AddToCartArgs -> String) args
        restName = (restaurantName :: AddToCartArgs -> String) args
        defaultUser = "IMAN"


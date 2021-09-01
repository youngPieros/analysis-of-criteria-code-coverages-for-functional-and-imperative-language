{-# LANGUAGE DuplicateRecordFields #-}

module Application
( Application.addRestaurant
, Application.addFood
, getRestaurants
, Application.getRestaurant
, Application.getFood
, addToCart
, getCart
, finalizeOrder
, getRecommendedRestaurants
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



locationMapper :: LocationArgs -> Location
locationMapper location = Location{x = xPos, y = yPos}
    where
        yPos = (y :: LocationArgs -> Int) location
        xPos = (x :: LocationArgs -> Int) location

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

toDTO :: ToJSON a => a -> String
toDTO obj = (C.unpack . encode) $ obj

addRestaurant :: Command -> DataBase -> (DataBase, Response)
addRestaurant (AddRestaurant (AddRestaurantArgs name description location menu)) db
    | elem restaurant (restaurants db) = (db, Response ("there is " ++ name ++ " already in restaurants"))
    | otherwise = (DataBase.addRestaurant db restaurant, Response "")
    where
        restaurant = Restaurant{name=name, description=description, location=restaurantLocation, menu=restaurantMenu}
        restaurantMenu = map restaurantFoodMapper menu
        restaurantLocation = locationMapper location


addFood :: Command -> DataBase -> (DataBase, Response)
addFood (AddFood args) db
    | restaurant == EmptyRestaurant = (db, Response ("there is not " ++ restName ++ " restaurant"))
    | elem food ((menu :: Restaurant -> [Food]) restaurant) = (db, Response ("there is " ++ ((name :: Food -> String) food) ++ " already in " ++ restName))
    | otherwise = (updateRestaurant db (Restaurant.addFood restaurant food), Response "")
    where
        restaurant = DataBase.getRestaurant db restName
        food = foodMapper args
        restName = (restaurantName :: AddFoodArgs -> String) args


getRestaurants :: Command -> DataBase -> (DataBase, Response)
getRestaurants GetRestaurants database = (database, Response response)
    where
        response = unwords $ restaurantNames
        restaurantNames = map (\restaurant -> (name :: Restaurant -> String) restaurant) (restaurants database)


getRestaurant :: Command -> DataBase -> (DataBase, Response)
getRestaurant (GetRestaurant args) database
    | restaurant == EmptyRestaurant = (database, Response ("there is not " ++ restaurantName ++ " restaurant"))
    | otherwise = (database, Response (toDTO restaurant))
    where
        restaurant = DataBase.getRestaurant database restaurantName
        restaurantName = (name :: GetRestaurantArgs -> String) args


getFood :: Command -> DataBase -> (DataBase, Response)
getFood (GetFood args) database
    | food == EmptyFood = (database, Response "there is not this food")
    | otherwise = (database, Response (toDTO food))
    where
        food = findFood database restName fName
        fName = (foodName :: GetFoodArgs -> String) args
        restName = (restaurantName :: GetFoodArgs -> String) args


addToCart :: Command -> DataBase -> (DataBase, Response)
addToCart (AddToCart args) db
    | basket /= EmptyOrder && (restaurantName :: Order -> String) basket /= restName = (db, Response "you can't order food from different restaurants")
    | food == EmptyFood = (db, Response "there is not this order")
    | basket == EmptyOrder = (updateOrder db (initOrder defaultUser restName fName 1), Response "")
    | otherwise = (updateOrder db (addOrder fName 1 basket), Response "")
    where
        basket = getUserBasket db defaultUser
        food = findFood db restName fName
        fName = (foodName :: AddToCartArgs -> String) args
        restName = (restaurantName :: AddToCartArgs -> String) args
        defaultUser = "IMAN"


getCart :: Command -> DataBase -> (DataBase, Response)
getCart GetCart db
    | basket == EmptyOrder = (db, Response "empty order!")
    | otherwise = (db, Response (toDTO basket))
    where
        basket = getUserBasket db defaultUser
        defaultUser = "IMAN"


finalizeOrder :: Command -> DataBase -> (DataBase, Response)
finalizeOrder FinalizeOrder db
    | basket == EmptyOrder = (db, Response "there is no order")
    | otherwise = (removeOrder db basket, Response (toDTO basket ++ "\nYour order has been successfully registered"))
    where
        basket = getUserBasket db defaultUser
        defaultUser = "IMAN"


getRecommendedRestaurants :: Command -> DataBase -> (DataBase, Response)
getRecommendedRestaurants GetRecommendedRestaurants db = (db, Response (unwords topRestaurants))
    where
        topRestaurants = map (snd) $ take topRestaurantNumbers sortedRestaurants
        sortedRestaurants = reverse $ Data.List.sort restaurantsPopularity
        restaurantsPopularity = map (\r -> (getPopularity r defaultLocation, (name :: Restaurant -> String) r)) (restaurants db)
        defaultLocation = Location{x=0, y=0}
        topRestaurantNumbers = 3

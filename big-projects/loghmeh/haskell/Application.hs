{-# LANGUAGE DuplicateRecordFields #-}

module Application
( addRestaurant
) where

import Food
import Location
import DataBase
import Response
import CommandArgument
import Restaurant


locationMapper :: LocationArgs -> Location
locationMapper location = Location{x = xPos, y = yPos}
    where
        yPos = (y :: LocationArgs -> Int) (location :: LocationArgs)
        xPos = (x :: LocationArgs -> Int) (location :: LocationArgs)

foodMapper :: RestaurantFoodArgs -> Food
foodMapper food = Food{name=foodName, description=foodDescription, popularity=foodPopularity, price=foodPrice}
    where
        foodName = (name :: RestaurantFoodArgs -> String) food
        foodPopularity = (price :: RestaurantFoodArgs -> Double) food
        foodPrice = (price :: RestaurantFoodArgs -> Double) food
        foodDescription = (description :: RestaurantFoodArgs -> String) food

addRestaurant :: Command -> DataBase -> (DataBase, Response)
addRestaurant (AddRestaurant (AddRestaurantArgs name description location menu)) db = (database, Response response)
    where
        response = if isRestaurantAlreadyInDB then ("there is " ++ name ++ " already in restaurants") else ""
        database = if isRestaurantAlreadyInDB then db else DataBase{restaurants=(restaurant:restaurants db), orders=(orders db)}
        isRestaurantAlreadyInDB = elem restaurant (restaurants db)
        restaurant = Restaurant{name=name, description=description, location=restaurantLocation, menu=restaurantMenu}
        restaurantMenu = map foodMapper menu
        restaurantLocation = locationMapper location

{-# LANGUAGE OverloadedStrings #-}

module Restaurant
( Restaurant(..)
, getFood
, getPopularity
, addFood
) where


import Data.Aeson
import Data.Maybe
import Data.List

import qualified Food
import Location



data Restaurant = Restaurant { name :: String
                             , description :: String
                             , location :: Location
                             , menu :: [Food.Food]
                             }
                  | EmptyRestaurant

instance Eq Restaurant where
    EmptyRestaurant == EmptyRestaurant = True
    Restaurant n1 _ _ _ == Restaurant n2 _ _ _ = n1 == n2
    _ == _ = False

instance ToJSON Restaurant where
    toJSON (Restaurant name description location menu) =
        object ["name" .= name, "description" .= description, "location" .= location, "menu" .= menu]



getPopularity :: Restaurant -> Location -> Double
getPopularity restaurant userLocation
    | foods == [] = 0
    | otherwise = sumPopularity / (fromIntegral $ length foods) / distanceFromUser
    where
        sumPopularity = foldl (\acc f -> acc + ((Food.popularity :: Food.Food -> Double) f)) 0.0 foods
        foods = menu restaurant
        distanceFromUser = distance userLocation (location restaurant)

getFood :: Restaurant -> String -> Food.Food
getFood EmptyRestaurant _ = Food.EmptyFood
getFood restaurant foodName
    | Data.Maybe.isJust food = Data.Maybe.fromJust food
    | otherwise = Food.EmptyFood
    where
        food = Data.List.find (\f -> (Food.name :: Food.Food -> String) f == foodName) (menu restaurant)

addFood :: Restaurant -> Food.Food -> Restaurant
addFood restaurant food = Restaurant{name=(name restaurant), description=(description restaurant), location=(location restaurant), menu=updatedMenu}
    where
        updatedMenu = food:(menu restaurant)

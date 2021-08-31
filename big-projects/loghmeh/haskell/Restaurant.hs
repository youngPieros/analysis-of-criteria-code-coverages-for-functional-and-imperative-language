{-# LANGUAGE DeriveGeneric #-}

module Restaurant
( Restaurant(..)
, getFood
) where


import GHC.Generics
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
                  | EmptyRestaurant deriving (Show, Generic)

instance Eq Restaurant where
    EmptyRestaurant == EmptyRestaurant = True
    EmptyRestaurant == Restaurant _ _ _ _ = False
    Restaurant _ _ _ _ == EmptyRestaurant = False
    r1 == r2 = Restaurant.name r1 == Restaurant.name r2

instance ToJSON Restaurant where
    toEncoding = genericToEncoding defaultOptions



getFood :: Restaurant -> String -> Food.Food
getFood EmptyRestaurant _ = Food.EmptyFood
getFood restaurant foodName
    | Data.Maybe.isJust food = Data.Maybe.fromJust food
    | otherwise = Food.EmptyFood
    where
        food = Data.List.find (\f -> (Food.name :: Food.Food -> String) f == foodName) (menu restaurant)


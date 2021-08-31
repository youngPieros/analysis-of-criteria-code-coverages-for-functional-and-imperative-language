{-# LANGUAGE DeriveGeneric #-}

module Restaurant
( Restaurant(..)
) where


import GHC.Generics
import Data.Aeson

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

getPopularity :: Restaurant -> Double
getPopularity restaurant = 0.0

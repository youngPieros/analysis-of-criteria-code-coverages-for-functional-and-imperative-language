{-# LANGUAGE DeriveGeneric #-}

module Restaurant
( Restaurant(..)
) where


import GHC.Generics
import Data.Aeson

import Food
import Location


data Restaurant = Restaurant { name :: String
                             , description :: String
                             , location :: Location
                             , menu :: [Food]
                             } deriving (Show, Generic)


instance Eq Restaurant where
 r1 == r2 = Restaurant.name r1 == Restaurant.name r2

instance ToJSON Restaurant where
    toEncoding = genericToEncoding defaultOptions

getPopularity :: Restaurant -> Double
getPopularity restaurant = 0.0

{-# LANGUAGE DuplicateRecordFields #-}
{-# LANGUAGE OverloadedStrings #-}

module CommandArgument
( AddRestaurantArgs(..)
, LocationArgs(..)
, RestaurantFoodArgs(..)
, AddFoodArgs(..)
, GetRestaurantArgs(..)
, GetFoodArgs(..)
, AddToCartArgs(..)
, Command(..)
) where


import Control.Applicative
import Control.Monad
import Data.Aeson

data LocationArgs = LocationArgs { x :: Int, y :: Int } deriving (Eq, Show, Read)
instance FromJSON LocationArgs where
    parseJSON (Object v) = LocationArgs <$> v .: "x" <*> v .: "y"
    parseJSON _          = mzero

data RestaurantFoodArgs = RestaurantFoodArgs { name :: String
                                             , description :: String
                                             , popularity :: Double
                                             , price :: Double
                                             } deriving (Eq, Show, Read)
instance FromJSON RestaurantFoodArgs where
    parseJSON (Object v) = RestaurantFoodArgs <$> v .: "name" <*> v .: "description" <*> v .: "popularity" <*> v .: "price"
    parseJSON _          = mzero

data AddRestaurantArgs = AddRestaurantArgs { name :: String
                                           , description :: String
                                           , location :: LocationArgs
                                           , menu :: [RestaurantFoodArgs]
                                           } deriving (Eq, Show, Read)
instance FromJSON AddRestaurantArgs where
    parseJSON (Object v) = AddRestaurantArgs <$> v .: "name" <*> v .: "description" <*> v .: "location" <*> v .: "menu"
    parseJSON _          = mzero


data AddFoodArgs = AddFoodArgs { name :: String
                               , restaurantName :: String
                               , description :: String
                               , popularity :: Double
                               , price :: Double
                                } deriving (Eq, Show, Read)


data GetRestaurantArgs = GetRestaurantArgs { name :: String } deriving (Eq, Show, Read)
instance FromJSON GetRestaurantArgs where
    parseJSON (Object v) = GetRestaurantArgs <$> v .: "name"
    parseJSON _          = mzero


data GetFoodArgs = GetFoodArgs { foodName :: String, restaurantName :: String } deriving (Eq, Show, Read)
instance FromJSON GetFoodArgs where
    parseJSON (Object v) = GetFoodArgs <$> v .: "foodName" <*> v .: "restaurantName"
    parseJSON _          = mzero


data AddToCartArgs = AddToCartArgs { foodName :: String, restaurantName :: String } deriving (Eq, Show, Read)
instance FromJSON AddToCartArgs where
    parseJSON (Object v) = AddToCartArgs <$> v .: "foodName" <*> v .: "restaurantName"
    parseJSON _          = mzero


data Command =
    AddRestaurant AddRestaurantArgs |
    AddFood AddFoodArgs |
    GetRestaurants |
    GetRestaurant GetRestaurantArgs |
    GetFood GetFoodArgs |
    AddToCart AddToCartArgs |
    GetCart |
    FinalizeOrder |
    GetRecommendedRestaurants |
    BadCommand deriving (Eq, Show, Read)


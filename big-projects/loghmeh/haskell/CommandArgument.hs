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
data RestaurantFoodArgs = RestaurantFoodArgs { name :: String
                                             , description :: String
                                             , popularity :: Double
                                             , price :: Double
                                             } deriving (Eq, Show, Read)
data AddRestaurantArgs = AddRestaurantArgs { name :: String
                                           , description :: String
                                           , location :: LocationArgs
                                           , menu :: [RestaurantFoodArgs]
                                           } deriving (Eq, Show, Read)


data AddFoodArgs = AddFoodArgs { name :: String
                               , restaurantName :: String
                               , description :: String
                               , popularity :: Double
                               , price :: Double
                                } deriving (Eq, Show, Read)


data GetRestaurantArgs = GetRestaurantArgs { name :: String } deriving (Eq, Show, Read)


data GetFoodArgs = GetFoodArgs { foodName :: String, restaurantName :: String } deriving (Eq, Show, Read)


data AddToCartArgs = AddToCartArgs { foodName :: String, restaurantName :: String } deriving (Eq, Show, Read)



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


instance FromJSON LocationArgs where
    parseJSON (Object v) = LocationArgs <$> v .: "x" <*> v .: "y"
    parseJSON _          = mzero

instance FromJSON RestaurantFoodArgs where
    parseJSON (Object v) = RestaurantFoodArgs <$> v .: "name" <*> v .: "description" <*> v .: "popularity" <*> v .: "price"
    parseJSON _          = mzero

instance FromJSON AddRestaurantArgs where
    parseJSON (Object v) = AddRestaurantArgs <$> v .: "name" <*> v .: "description" <*> v .: "location" <*> v .: "menu"
    parseJSON _          = mzero


instance FromJSON AddFoodArgs where
    parseJSON (Object v) = AddFoodArgs <$> v .: "name" <*> v .: "restaurantName" <*> v .: "description" <*> v .: "popularity" <*> v .: "price"


instance FromJSON GetRestaurantArgs where
    parseJSON (Object v) = GetRestaurantArgs <$> v .: "name"
    parseJSON _          = mzero


instance FromJSON GetFoodArgs where
    parseJSON (Object v) = GetFoodArgs <$> v .: "foodName" <*> v .: "restaurantName"
    parseJSON _          = mzero


instance FromJSON AddToCartArgs where
    parseJSON (Object v) = AddToCartArgs <$> v .: "foodName" <*> v .: "restaurantName"
    parseJSON _          = mzero

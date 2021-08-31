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




data LocationArgs = LocationArgs { x :: Int, y :: Int }
data RestaurantFoodArgs = RestaurantFoodArgs { name :: String
                                             , description :: String
                                             , popularity :: Double
                                             , price :: Double
                                             }
data AddRestaurantArgs = AddRestaurantArgs { name :: String
                                           , description :: String
                                           , location :: LocationArgs
                                           , menu :: [RestaurantFoodArgs]
                                           }


data AddFoodArgs = AddFoodArgs { name :: String
                               , restaurantName :: String
                               , description :: String
                               , popularity :: Double
                               , price :: Double
                                }


data GetRestaurantArgs = GetRestaurantArgs { name :: String }


data GetFoodArgs = GetFoodArgs { foodName :: String, restaurantName :: String }


data AddToCartArgs = AddToCartArgs { foodName :: String, restaurantName :: String }



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
    BadCommand


instance FromJSON LocationArgs where
    parseJSON (Object v) = LocationArgs <$> v .: "x" <*> v .: "y"

instance FromJSON RestaurantFoodArgs where
    parseJSON (Object v) = RestaurantFoodArgs <$> v .: "name" <*> v .: "description" <*> v .: "popularity" <*> v .: "price"

instance FromJSON AddRestaurantArgs where
    parseJSON (Object v) = AddRestaurantArgs <$> v .: "name" <*> v .: "description" <*> v .: "location" <*> v .: "menu"

instance FromJSON AddFoodArgs where
    parseJSON (Object v) = AddFoodArgs <$> v .: "name" <*> v .: "restaurantName" <*> v .: "description" <*> v .: "popularity" <*> v .: "price"

instance FromJSON GetRestaurantArgs where
    parseJSON (Object v) = GetRestaurantArgs <$> v .: "name"

instance FromJSON GetFoodArgs where
    parseJSON (Object v) = GetFoodArgs <$> v .: "foodName" <*> v .: "restaurantName"

instance FromJSON AddToCartArgs where
    parseJSON (Object v) = AddToCartArgs <$> v .: "foodName" <*> v .: "restaurantName"

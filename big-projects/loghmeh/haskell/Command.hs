{-# LANGUAGE DuplicateRecordFields #-}
{-# LANGUAGE OverloadedStrings #-}

module Command
( Command(..)
, getCommands
) where


import qualified Data.ByteString.Lazy.Char8 as C
import Control.Monad.State (State, evalState)
import Data.Maybe
import Data.Aeson
import Data.List
import ReaderLib
import CommandArgument


getAddRestaurantArgs :: String -> AddRestaurantArgs
getAddRestaurantArgs json = Data.Maybe.fromJust (decode (C.pack json) :: Maybe AddRestaurantArgs)

getAddFoodArgs :: String -> AddFoodArgs
getAddFoodArgs json = Data.Maybe.fromJust (decode (C.pack json) :: Maybe AddFoodArgs)

getGetRestaurantArgs :: String -> GetRestaurantArgs
getGetRestaurantArgs json = Data.Maybe.fromJust (decode (C.pack json) :: Maybe GetRestaurantArgs)

getGetFoodArgs :: String -> GetFoodArgs
getGetFoodArgs json = Data.Maybe.fromJust (decode (C.pack json) :: Maybe GetFoodArgs)

getAddToCartArgs :: String -> AddToCartArgs
getAddToCartArgs json = Data.Maybe.fromJust (decode (C.pack json) :: Maybe AddToCartArgs)


selectCommand :: String -> String -> Command
selectCommand commandType args
    | commandType == "addRestaurant" = AddRestaurant (getAddRestaurantArgs args)
    | commandType == "addFood" = AddFood (getAddFoodArgs args)
    | commandType == "getRestaurants" = GetRestaurants
    | commandType == "getRestaurant" = GetRestaurant (getGetRestaurantArgs args)
    | commandType == "getFood" = GetFood (getGetFoodArgs args)
    | commandType == "addToCart" = AddToCart (getAddToCartArgs args)
    | commandType == "getCart" = GetCart
    | commandType == "finalizeOrder" = FinalizeOrder
    | commandType == "getRecommendedRestaurants" = GetRecommendedRestaurants
    | otherwise = BadCommand

command :: Scanner Command
command = do
  commandStr <- line
  let commandType = head . words $ commandStr
  let args = unwords . tail . words $ commandStr
  return (selectCommand commandType args)

getCommands :: Scanner [Command]
getCommands = do
  commands <- many command
  return commands

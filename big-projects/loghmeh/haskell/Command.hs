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


getAddRestaurantArgs :: String -> Command
getAddRestaurantArgs json = if Data.Maybe.isJust args then AddRestaurant (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddRestaurantArgs)

getAddFoodArgs :: String -> Command
getAddFoodArgs json = if Data.Maybe.isJust args then AddFood (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddFoodArgs)

getGetRestaurantArgs :: String -> Command
getGetRestaurantArgs json = if Data.Maybe.isJust args then GetRestaurant (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe GetRestaurantArgs)

getGetFoodArgs :: String -> Command
getGetFoodArgs json = if Data.Maybe.isJust args then GetFood (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe GetFoodArgs)

getAddToCartArgs :: String -> Command
getAddToCartArgs json = if Data.Maybe.isJust args then AddToCart (Data.Maybe.fromJust args) else BadCommand
    where
        args = (decode (C.pack json) :: Maybe AddToCartArgs)


selectCommand :: String -> String -> Command
selectCommand commandType args
    | commandType == "addRestaurant" = getAddRestaurantArgs args
    | commandType == "addFood" = getAddFoodArgs args
    | commandType == "getRestaurants" = GetRestaurants
    | commandType == "getRestaurant" = getGetRestaurantArgs args
    | commandType == "getFood" = getGetFoodArgs args
    | commandType == "addToCart" = getAddToCartArgs args
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

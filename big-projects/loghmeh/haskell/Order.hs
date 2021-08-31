{-# LANGUAGE DeriveGeneric #-}

module Order
( Order(..)
, initOrder
, addOrder
) where

import GHC.Generics
import Data.Aeson
import Data.Map
import Data.Maybe

data Order = Order { user :: String
                   , restaurantName :: String
                   , basket :: Data.Map.Map String Int
                   }
            | EmptyOrder deriving (Show, Generic)

instance Eq Order where
    EmptyOrder == EmptyOrder = True
    EmptyOrder == Order _ _ _ = False
    Order _ _ _ == EmptyOrder = False
    o1 == o2 = Order.user o1 == Order.user o2

instance ToJSON Order where
    toEncoding = genericToEncoding defaultOptions



addOrder :: String -> Int -> Order -> Order
addOrder food number order = Order{user=(user order), restaurantName=(restaurantName order), basket=newOrders}
 where
    newOrders = Data.Map.insert food orderNumber currentOrders
    orderNumber = number + (if Data.Maybe.isJust currentOrder then Data.Maybe.fromJust currentOrder else 0)
    currentOrder = Data.Map.lookup food currentOrders
    currentOrders = basket order

initOrder :: String -> String -> String -> Int -> Order
initOrder user restaurantName food number = Order{user=user, restaurantName=restaurantName, basket=initializedBasket}
    where
        initializedBasket = Data.Map.fromList [(food, number)]

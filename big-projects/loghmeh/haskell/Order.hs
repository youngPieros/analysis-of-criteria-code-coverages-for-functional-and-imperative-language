{-# LANGUAGE DeriveGeneric #-}

module Order
( Order(..)
, emptyOrder
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

--addFoodToOrder :: String -> Int -> Order -> Order
--addFoodToOrder food number order = Order{user=(user order), restaurantName=(restaurantName order), orders=newOrders}
-- where
--    newOrders = Data.Map.insert food orderNumber (orders order)
--    orderNumber = number + (if Data.Maybe.isJust currentOrder then Data.Maybe.fromJust currentOrder else 0)
--    currentOrder = Data.Map.lookup food
--    currentOrders = orders order
--
--
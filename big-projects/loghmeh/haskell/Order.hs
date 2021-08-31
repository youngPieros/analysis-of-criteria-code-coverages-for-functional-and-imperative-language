{-# LANGUAGE OverloadedStrings #-}

module Order
( Order(..)
, initOrder
, addOrder
) where


import Data.Aeson
import Data.Map
import Data.Maybe



data Order = Order { user :: String
                   , restaurantName :: String
                   , basket :: Data.Map.Map String Int
                   }
            | EmptyOrder

instance Eq Order where
    EmptyOrder == EmptyOrder = True
    Order u1 _ _ == Order u2 _ _ = u1 == u2
    _ == _ = False

instance ToJSON Order where
    toJSON (Order user restaurantName basket) = object ["orders" .= basket]



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

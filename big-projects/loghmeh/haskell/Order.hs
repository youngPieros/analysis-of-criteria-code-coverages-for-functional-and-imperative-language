module Order
( Order(..)
, createOrder
) where
import Data.Map
import Data.Maybe

data Order = Order { user :: String
                   , restaurantName :: String
                   , orders :: Data.Map.Map String Int
                   } deriving (Show)


createOrder :: String -> String -> Order
createOrder user restaurantName = Order {user=user, restaurantName=restaurantName, orders=Data.Map.empty}

--addFoodToOrder :: String -> Int -> Order -> Order
--addFoodToOrder food number order = Order{user=(user order), restaurantName=(restaurantName order), orders=newOrders}
-- where
--    newOrders = Data.Map.insert food orderNumber (orders order)
--    orderNumber = number + (if Data.Maybe.isJust currentOrder then Data.Maybe.fromJust currentOrder else 0)
--    currentOrder = Data.Map.lookup food
--    currentOrders = orders order
--
--
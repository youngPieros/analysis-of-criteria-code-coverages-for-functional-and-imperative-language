module Restaurant
( Restaurant(..)
) where
import Food
import Location


data Restaurant = Restaurant { name :: String
                             , description :: String
                             , location :: Location
                             , menu :: [Food]
                             } deriving (Show)


instance Eq Restaurant where
 r1 == r2 = Restaurant.name r1 == Restaurant.name r2

getPopularity :: Restaurant -> Double
getPopularity restaurant = 0.0

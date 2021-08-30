module Location
( Location(..)
, showLocation
) where

data Location = Locations { x :: Int
                         , y :: Int
                         } deriving (Show, Eq)

showLocation :: Location -> String
showLocation l = "x: " ++ (show (x l) :: String) ++ ", y: " ++ (show (y l) :: String)

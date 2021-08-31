{-# LANGUAGE OverloadedStrings #-}

module Location
( Location(..)
, distance
) where


import Data.Aeson



data Location = Location { x :: Int
                         , y :: Int
                         }

instance ToJSON Location where
    toJSON (Location x y) = object ["x" .= x, "y" .= y]



distance :: Location -> Location -> Double
distance l1 l2 = sqrt $ fromIntegral $ (+) ((^2) $ (-) (x l1) (x l2)) ((^2) $ (-) (y l1) (y l2))

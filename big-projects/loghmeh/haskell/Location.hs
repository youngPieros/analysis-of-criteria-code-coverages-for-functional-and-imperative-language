{-# LANGUAGE DeriveGeneric #-}

module Location
( Location(..)
, distance
) where

import GHC.Generics
import Data.Aeson

data Location = Location { x :: Int
                         , y :: Int
                         } deriving (Eq, Show, Generic)


instance ToJSON Location where
    toEncoding = genericToEncoding defaultOptions


distance :: Location -> Location -> Double
distance l1 l2 = sqrt $ fromIntegral $ (+) ((^2) $ (-) (x l1) (x l2)) ((^2) $ (-) (y l1) (y l2))

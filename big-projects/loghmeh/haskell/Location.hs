{-# LANGUAGE DeriveGeneric #-}

module Location
( Location(..)
) where

import GHC.Generics
import Data.Aeson

data Location = Location { x :: Int
                         , y :: Int
                         } deriving (Eq, Show, Generic)


instance ToJSON Location where
    toEncoding = genericToEncoding defaultOptions



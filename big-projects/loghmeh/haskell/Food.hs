{-# LANGUAGE DeriveGeneric #-}

module Food
( Food(..)
) where


import GHC.Generics
import Data.Aeson


data Food = Food { name :: String
                 , description :: String
                 , popularity :: Double
                 , price :: Double
                 } deriving (Show, Generic)


instance Eq Food where
 f1 == f2 = name f1 == name f2

instance ToJSON Food where
    toEncoding = genericToEncoding defaultOptions

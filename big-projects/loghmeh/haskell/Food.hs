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
                 } | EmptyFood deriving (Show, Generic)


instance Eq Food where
    EmptyFood == EmptyFood = True
    EmptyFood == Food _ _ _ _ = False
    Food _ _ _ _ == EmptyFood = False
    f1 == f2 = name f1 == name f2

instance ToJSON Food where
    toEncoding = genericToEncoding defaultOptions

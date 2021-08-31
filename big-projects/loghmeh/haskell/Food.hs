{-# LANGUAGE OverloadedStrings #-}

module Food
( Food(..)
) where


import Data.Aeson



data Food = Food { name :: String
                 , description :: String
                 , popularity :: Double
                 , price :: Double
                 } | EmptyFood deriving (Show)

instance Eq Food where
    EmptyFood == EmptyFood = True
    EmptyFood == Food _ _ _ _ = False
    Food _ _ _ _ == EmptyFood = False
    f1 == f2 = name f1 == name f2

instance ToJSON Food where
    toJSON (Food name description popularity price) =
        object ["name" .= name, "description" .= description, "popularity" .= popularity, "price" .= price]


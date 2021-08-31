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
    Food n1 _ _ _ == Food n2 _ _ _ = n1 == n2
    _ == _ = False

instance ToJSON Food where
    toJSON (Food name description popularity price) =
        object ["name" .= name, "description" .= description, "popularity" .= popularity, "price" .= price]


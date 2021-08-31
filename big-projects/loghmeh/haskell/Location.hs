module Location
( Location(..)
) where

data Location = Location { x :: Int
                         , y :: Int
                         } deriving (Show, Eq)


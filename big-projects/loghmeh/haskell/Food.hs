module Food
( Food(..)
) where


data Food = Food { name :: String
                 , description :: String
                 , popularity :: Double
                 , price :: Double
                 } deriving (Show)


instance Eq Food where
 f1 == f2 = name f1 == name f2


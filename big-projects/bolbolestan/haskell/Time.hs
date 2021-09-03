module Time
( Time(..)
) where


import Data.List.Split



data Time = Time { hour :: Int
                 , minute :: Int
                 }

instance Eq Time where
    Time h1 m1 == Time h2 m2 = (h1 == h2) && (m1 == m2)

instance Show Time where
    show (Time hour minute) = ((show hour :: String) ++ ":" ++ (show minute :: String))

instance Read Time where
    readsPrec _ time = [(Time{hour=hour, minute=minute}, "")]
        where
            [hour, minute] = map (\arg -> read arg :: Int) (take 2 params)
            params = (splitOn ":" time) ++ ["0"]

instance Ord Time where
    compare t1 t2
        | hourComparison == EQ = compare (minute t1) (minute t2)
        | otherwise = hourComparison
        where
            hourComparison = compare (hour t1) (hour t2)


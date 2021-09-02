module DateTime
( DateTime(..)
) where


import Data.List.Split


data DateTime = DateTime { year :: Int
                         , month :: Int
                         , day :: Int
                         , hour :: Int
                         , minute :: Int
                         , second :: Int
                         }

instance Show DateTime where
    show (DateTime year month day hour minute second) = date ++ "T" ++ time
        where
            date = (showTimeParams year) ++ "-" ++ (showTimeParams month) ++ "-" ++ (showTimeParams day)
            time = (showTimeParams hour) ++ ":" ++ (showTimeParams minute) ++ ":" ++ (showTimeParams second)

instance Read DateTime where
    readsPrec _ datetime = [(DateTime{year=year, month=month, day=day, hour=hour, minute=minute, second=second}, "")]
        where
            [year, month, day, hour, minute, second] = map (\arg -> read arg :: Int) (dateParams ++ timeParams)
            timeParams = map (dropWhile (\c -> c == '0')) $ splitOn "-" date
            dateParams = map (dropWhile (\c -> c == '0')) $ splitOn "-" date
            [date, time] = splitWhen (\c -> c == 'T') datetime

instance Eq DateTime where
    DateTime y1 m1 d1 h1 mi1 s1 == DateTime y2 m2 d2 h2 mi2 s2 = (y1 == y2) && (m1 == m2) && (d1 == d2) && (h1 == h2) && (mi1 == mi2) && (s1 == s2)

instance Ord DateTime where
    compare t1 t2
        | t1 == t2 = EQ
        | yearComparison /= EQ = yearComparison
        | monthComparison /= EQ = monthComparison
        | dayComparison /= EQ = dayComparison
        | hourComparison /= EQ = hourComparison
        | minuteComparison /= EQ = minuteComparison
        | otherwise = compare (second t1) (second t2)
        where
            yearComparison = compare (year t1) (year t2)
            monthComparison = compare (month t1) (month t2)
            dayComparison = compare (day t1) (day t2)
            hourComparison = compare (hour t1) (hour t2)
            minuteComparison = compare (minute t1) (minute t2)


showTimeParams :: Int -> String
showTimeParams parameter = (if parameter < 10 then "0" else "") ++ stringParameter
    where stringParameter = show parameter :: String


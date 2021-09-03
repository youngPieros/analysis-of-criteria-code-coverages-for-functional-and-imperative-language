{-# LANGUAGE OverloadedStrings #-}

module RangeTime
( RangeTime(..)
, conflict
) where


import Data.List.Split
import Data.Aeson

import Time



data RangeTime = RangeTime { start :: Time
                           , end :: Time
                           }

instance Eq RangeTime where
    RangeTime s1 e1 == RangeTime s2 e2 = (s1 == s2) && (e1 == e2)

instance Show RangeTime where
    show (RangeTime start end) = ((show :: Time -> String) start) ++ "-" ++ ((show :: Time -> String) end)

instance Read RangeTime where
    readsPrec _ rangeTime = [(RangeTime{start=start, end=end}, "")]
        where
            [start, end] = map (\param -> (read param :: Time)) $ splitOn "-" rangeTime


conflict :: RangeTime -> RangeTime -> Bool
conflict (RangeTime s1 e1) (RangeTime s2 e2) = (s1 <= s2 && s2 < e1) || (s2 <= s1 && s1 < e2)


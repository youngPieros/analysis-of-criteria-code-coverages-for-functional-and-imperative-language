{-# LANGUAGE OverloadedStrings #-}

module RangeTime
( RangeTime(..)
, conflict
) where


import Time
import Data.Aeson



data RangeTime = RangeTime { start :: Time
                           , end :: Time
                           }

instance Eq RangeTime where
    RangeTime s1 e1 == RangeTime s2 e2 = (s1 == s2) && (e1 == e2)

instance Show RangeTime where
    show (RangeTime start end) = ((show :: Time -> String) start) ++ "-" ++ ((show :: Time -> String) end)


conflict :: RangeTime -> RangeTime -> Bool
conflict (RangeTime s1 e1) (RangeTime s2 e2) = (s1 <= s2 && s2 < e1) || (s2 <= s1 && s1 < e2)


{-# LANGUAGE OverloadedStrings #-}

module ClassTime
( ClassTime(..)
, ClassTime.conflict
) where


import qualified Data.ByteString.Lazy.Char8 as C
import Data.Aeson

import Day
import RangeTime



data ClassTime = ClassTime { days :: [Day]
                           , time :: RangeTime
                           }

instance ToJSON ClassTime where
    toJSON (ClassTime days time) =
        object ["days" .= (show days :: String), "time" .= (show time :: String)]


conflict :: ClassTime -> ClassTime -> Bool
conflict (ClassTime d1 t1) (ClassTime d2 t2) = if haveCommonDays then RangeTime.conflict t1 t2 else False
    where
        haveCommonDays = any (\d -> elem d d2) d1


{-# LANGUAGE OverloadedStrings #-}

module ExamTime
( ExamTime(..)
, conflict
) where


import Data.Aeson

import DateTime



data ExamTime = ExamTime { start :: DateTime
                         , end :: DateTime
                         }

instance ToJSON ExamTime where
    toJSON (ExamTime start end) =
        object ["start" .= (show start :: String), "end" .= (show end :: String)]


conflict :: ExamTime -> ExamTime -> Bool
conflict (ExamTime s1 e1) (ExamTime s2 e2) = (s1 <= s2 && s2 < e1) || (s2 <= s1 && s1 < e2)


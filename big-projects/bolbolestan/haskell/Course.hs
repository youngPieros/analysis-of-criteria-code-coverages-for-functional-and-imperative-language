{-# LANGUAGE OverloadedStrings #-}

module Course
( Course(..)
) where


import Data.Aeson

import ClassTime
import ExamTime



data Course = Course { code :: String
                     , name :: String
                     , instructor :: String
                     , units :: Int
                     , classTime :: ClassTime
                     , examTime :: ExamTime
                     , capacity :: Int
                     , prerequisites :: [String]
                     } | NullCourse

instance Eq Course where
    NullCourse == NullCourse = True
    c1 == c2 = code c1 == code c2
    _ == _ = False

instance ToJSON Course where
    toJSON (Course code name instructor units classTime examTime capacity prerequisites) =
        object ["code" .= code, "name" .= name, "instructor" .= instructor, "units" .= units, "classTime" .= classTime, "examTime" .= examTime, "capacity" .= capacity, "prerequisites" .= prerequisites]


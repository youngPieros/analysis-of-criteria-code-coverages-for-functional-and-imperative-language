{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE DuplicateRecordFields #-}

module Course
( Course(..)
) where


import Data.Aeson

import ClassTime
import ExamTime
import RangeTime
import Time


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
    Course c1 _ i1 _ ct1 _ _ _ == Course c2 _ i2 _ ct2 _ _ _ = (c1 == c2) && (i1 == i2) && (ClassTime.conflict ct1 ct2)
    _ == _ = False

instance ToJSON Course where
    toJSON (Course code name instructor units classTime examTime capacity prerequisites) =
        object ["code" .= code, "name" .= name, "instructor" .= instructor, "units" .= units, "classTime" .= classTime, "examTime" .= examTime, "capacity" .= capacity, "prerequisites" .= prerequisites]


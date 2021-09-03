{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE DuplicateRecordFields #-}

module TermCourse
( TermCourse(..)
) where


import Data.Aeson

import ClassTime
import ExamTime
import TermCourseStatus

data TermCourse = TermCourse { code :: String
                             , name :: String
                             , instructor :: String
                             , classTime :: ClassTime
                             , examTime :: ExamTime
                             , status :: TermCourseStatus
                             }

instance Eq TermCourse where
    c1 == c2 = (code c1) == (code c2)

instance ToJSON TermCourse where
    toJSON (TermCourse code name instructor classTime examTime status) =
        object ["code" .= code, "name" .= name, "instructor" .= instructor, "classTime" .= classTime, "examTime" .= examTime, "status" .= (show status :: String)]


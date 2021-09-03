{-# LANGUAGE DuplicateRecordFields #-}
{-# LANGUAGE OverloadedStrings #-}

module CommandArguments
( StartEndArgument(..)
, ClassTimeArgument(..)
, AddCourseArgument(..)
, AddStudentArgument(..)
) where


import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe

import Control.Applicative
import Control.Monad
import Data.Aeson



data StartEndArgument = StartEndArgument { start :: String
                                         , end :: String
                                         } deriving(Show)

data ClassTimeArgument = ClassTimeArgument { days :: [String]
                                           , time :: String
                                           } deriving(Show)

data AddCourseArgument = AddCourseArgument { code :: String
                                           , name :: String
                                           , instructor :: String
                                           , units :: Int
                                           , classTime :: ClassTimeArgument
                                           , examTime :: StartEndArgument
                                           , capacity :: Int
                                           , prerequisites :: [String]
                                           } deriving(Show)

data AddStudentArgument = AddStudentArgument { studentId :: String
                                             , name :: String
                                             , enteredAt :: Int
                                             }



instance FromJSON StartEndArgument where
    parseJSON (Object v) = StartEndArgument <$> v .: "start" <*> v .: "end"

instance FromJSON ClassTimeArgument where
    parseJSON (Object v) = ClassTimeArgument <$> v .: "days" <*> v .: "time"

instance FromJSON AddCourseArgument where
    parseJSON (Object v) = AddCourseArgument <$> v .: "code" <*> v .: "name" <*> v .: "instructor" <*> v .: "units" <*> v .: "classTime" <*> v .: "examTime" <*> v .: "capacity" <*> v .: "prerequisites"

instance FromJSON AddStudentArgument where
    parseJSON (Object v) = AddStudentArgument <$> v .: "studentId" <*> v .: "name" <*> v .: "enteredAt"


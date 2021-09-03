{-# LANGUAGE DuplicateRecordFields #-}
{-# LANGUAGE OverloadedStrings #-}

module CommandArguments
( StartEndArgument(..)
, ClassTimeArgument(..)
, AddCourseArgument(..)
, AddStudentArgument(..)
, GetCoursesArgument(..)
, GetCourseArgument(..)
, AddToWeeklyScheduleArgument(..)
, RemoveFromWeeklyScheduleArgument(..)
, GetWeeklyScheduleArgument(..)
, FinalizeScheduleArgument(..)
) where


import qualified Data.ByteString.Lazy.Char8 as C
import Data.Maybe
import Control.Applicative
import Control.Monad
import Data.Aeson



data StartEndArgument = StartEndArgument { start :: String
                                         , end :: String
                                         } deriving (Show)

data ClassTimeArgument = ClassTimeArgument { days :: [String]
                                           , time :: String
                                           } deriving (Show)

data AddCourseArgument = AddCourseArgument { code :: String
                                           , name :: String
                                           , instructor :: String
                                           , units :: Int
                                           , classTime :: ClassTimeArgument
                                           , examTime :: StartEndArgument
                                           , capacity :: Int
                                           , prerequisites :: [String]
                                           } deriving (Show)

data AddStudentArgument = AddStudentArgument { studentId :: String
                                             , name :: String
                                             , enteredAt :: Int
                                             }

data GetCoursesArgument = GetCoursesArgument { studentId :: String }

data GetCourseArgument = GetCourseArgument { studentId :: String
                                           , code :: String
                                           }

data AddToWeeklyScheduleArgument = AddToWeeklyScheduleArgument { studentId :: String
                                                               , code :: String
                                                               }

data RemoveFromWeeklyScheduleArgument = RemoveFromWeeklyScheduleArgument { studentId :: String
                                                                         , code :: String
                                                                         }

data GetWeeklyScheduleArgument = GetWeeklyScheduleArgument { studentId :: String }

data FinalizeScheduleArgument = FinalizeScheduleArgument { studentId :: String }



instance FromJSON StartEndArgument where
    parseJSON (Object v) = StartEndArgument <$> v .: "start" <*> v .: "end"

instance FromJSON ClassTimeArgument where
    parseJSON (Object v) = ClassTimeArgument <$> v .: "days" <*> v .: "time"

instance FromJSON AddCourseArgument where
    parseJSON (Object v) = AddCourseArgument <$> v .: "code" <*> v .: "name" <*> v .: "instructor" <*> v .: "units" <*> v .: "classTime" <*> v .: "examTime" <*> v .: "capacity" <*> v .: "prerequisites"

instance FromJSON AddStudentArgument where
    parseJSON (Object v) = AddStudentArgument <$> v .: "studentId" <*> v .: "name" <*> v .: "enteredAt"

instance FromJSON GetCoursesArgument where
    parseJSON (Object v) = GetCoursesArgument <$> v .: "studentId"

instance FromJSON GetCourseArgument where
    parseJSON (Object v) = GetCourseArgument <$> v .: "studentId" <*> v .: "code"

instance FromJSON AddToWeeklyScheduleArgument where
    parseJSON (Object v) = AddToWeeklyScheduleArgument <$> v .: "studentId" <*> v .: "code"

instance FromJSON RemoveFromWeeklyScheduleArgument where
    parseJSON (Object v) = RemoveFromWeeklyScheduleArgument <$> v .: "studentId" <*> v .: "code"

instance FromJSON GetWeeklyScheduleArgument where
    parseJSON (Object v) = GetWeeklyScheduleArgument <$> v .: "studentId"

instance FromJSON FinalizeScheduleArgument where
    parseJSON (Object v) = FinalizeScheduleArgument <$> v .: "studentId"


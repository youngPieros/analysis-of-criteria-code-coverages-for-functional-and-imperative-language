module TermCourse
( TermCourse(..)
) where


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

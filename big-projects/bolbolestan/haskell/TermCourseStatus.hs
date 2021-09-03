module TermCourseStatus
( TermCourseStatus(..)
) where


data TermCourseStatus = Finalized | NonFinalized deriving (Show, Read, Enum)


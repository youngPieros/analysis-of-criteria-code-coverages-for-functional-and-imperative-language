module TermCourseStatus
( TermCourseStatus(..)
) where


data TermCourseStatus = Finalized | NonFinalized deriving (Read, Enum)

instance Show TermCourseStatus where
    show Finalized = "finalized"
    show NonFinalized = "non-finalized"

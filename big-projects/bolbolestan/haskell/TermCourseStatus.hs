module TermCourseStatus
( TermCourseStatus(..)
) where


data TermCourseStatus = Finalized | NonFinalized deriving (Read, Enum)

instance Eq TermCourseStatus where
    Finalized == Finalized = True
    NonFinalized == NonFinalized = True
    _ == _ = False

instance Show TermCourseStatus where
    show Finalized = "finalized"
    show NonFinalized = "non-finalized"

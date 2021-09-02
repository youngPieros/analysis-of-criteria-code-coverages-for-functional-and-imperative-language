module Day
( Day(..)
) where



data Day = Saturday | Sunday | Monday | Tuesday | Wednesday | Thursday | Friday deriving (Show, Read)

instance Eq Day where
    Saturday == Saturday  = True
    Sunday == Sunday  = True
    Monday == Monday  = True
    Tuesday == Tuesday  = True
    Wednesday == Wednesday  = True
    Thursday == Thursday  = True
    Friday == Friday = True
    _ == _ = False


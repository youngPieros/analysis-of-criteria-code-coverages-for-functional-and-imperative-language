module Day
( Day(..)
) where



data Day = Sunday | Monday | Tuesday | Wednesday | Thursday | Friday | Saturday deriving (Show, Read, Enum)

instance Eq Day where
    Saturday == Saturday  = True
    Sunday == Sunday  = True
    Monday == Monday  = True
    Tuesday == Tuesday  = True
    Wednesday == Wednesday  = True
    Thursday == Thursday  = True
    Friday == Friday = True
    _ == _ = False


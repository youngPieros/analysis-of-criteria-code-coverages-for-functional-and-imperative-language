module Day
( Day(..)
) where



data Day = Sunday | Monday | Tuesday | Wednesday | Thursday | Friday | Saturday deriving (Show, Read, Enum, Eq)


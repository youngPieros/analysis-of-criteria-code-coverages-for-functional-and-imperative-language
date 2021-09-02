module Command
( Command(..)
) where



import CommandArguments



data Command =
    AddCourse AddCourseArgument |
    BadCommand


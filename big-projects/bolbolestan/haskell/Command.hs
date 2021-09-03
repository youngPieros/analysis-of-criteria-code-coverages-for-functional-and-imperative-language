module Command
( Command(..)
) where



import CommandArguments



data Command =
    AddCourse AddCourseArgument |
    AddStudent AddStudentArgument |
    GetCourses GetCoursesArgument |
    BadCommand


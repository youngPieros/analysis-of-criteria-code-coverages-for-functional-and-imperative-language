module Command
( Command(..)
) where



import CommandArguments



data Command =
    AddCourse AddCourseArgument |
    AddStudent AddStudentArgument |
    GetCourses GetCoursesArgument |
    GetCourse GetCourseArgument |
    AddToWeeklySchedule AddToWeeklyScheduleArgument |
    RemoveFromWeeklySchedule RemoveFromWeeklyScheduleArgument |
    BadCommand


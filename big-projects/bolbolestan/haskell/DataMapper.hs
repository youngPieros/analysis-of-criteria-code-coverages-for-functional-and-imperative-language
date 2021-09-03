module DataMapper
( toCourse
) where


import Day
import RangeTime
import Time
import ClassTime
import DateTime
import ExamTime
import Course
import CommandArguments



toCourse :: AddCourseArgument -> Course
toCourse args = Course code name instructor units classTime examTime capacity prerequisites
    where
        code = (CommandArguments.code :: AddCourseArgument -> String) args
        name = (CommandArguments.name :: AddCourseArgument -> String) args
        instructor = (CommandArguments.instructor :: AddCourseArgument -> String) args
        units = (CommandArguments.units :: AddCourseArgument -> Int) args
        examTime = ExamTime (read startExam :: DateTime) (read endExam :: DateTime)
        startExam = (CommandArguments.start :: StartEndArgument -> String) exam
        endExam = (CommandArguments.end :: StartEndArgument -> String) exam
        exam = (CommandArguments.examTime :: AddCourseArgument -> StartEndArgument) args
        classTime = ClassTime classDays (read time :: RangeTime)
        classDays = map (\d -> read d :: Day) ((CommandArguments.days :: ClassTimeArgument -> [String]) ct)
        time = (CommandArguments.time :: ClassTimeArgument -> String) $ ct
        ct = (CommandArguments.classTime :: AddCourseArgument -> ClassTimeArgument) args
        capacity = (CommandArguments.capacity :: AddCourseArgument -> Int) args
        prerequisites = (CommandArguments.prerequisites :: AddCourseArgument -> [String]) args


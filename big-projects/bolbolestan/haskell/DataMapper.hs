{-# LANGUAGE DuplicateRecordFields #-}

module DataMapper
( toCourse
, toStudent
, toCourseSummary
) where


import Day
import RangeTime
import Time
import ClassTime
import DateTime
import ExamTime
import Course
import Student
import CommandArguments
import DTO


toCourse :: AddCourseArgument -> Course
toCourse args = Course courseCode courseName instructor units classTime examTime capacity prerequisites
    where
        courseCode = (code :: AddCourseArgument -> String) args
        courseName = (name :: AddCourseArgument -> String) args
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

toStudent :: AddStudentArgument -> Student
toStudent args = Student sid studentName studentEnteredAt
    where
        sid = (studentId :: AddStudentArgument -> String) args
        studentName = (name :: AddStudentArgument -> String) args
        studentEnteredAt = (enteredAt :: AddStudentArgument -> Int) args

toCourseSummary :: Course -> CourseSummary
toCourseSummary course = CourseSummary{code=courseCode, name=courseName, instructor=instructorName}
    where
        courseCode = (code :: Course -> String) course
        courseName = (name :: Course -> String) course
        instructorName = (instructor :: Course -> String) course

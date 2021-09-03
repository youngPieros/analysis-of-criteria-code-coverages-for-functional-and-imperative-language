module StudentScheduleCourse
( StudentScheduleCourse(..)
, createStudentScheduleCourse
, addTermCourse
, removeTermCourse
, getTermCourses
) where


import TermCourse



data StudentScheduleCourse = StudentScheduleCourse { studentId :: String
                                                   , termCourses :: [TermCourse]
                                                   } | NullSchedule


instance Eq StudentScheduleCourse where
    NullSchedule == NullSchedule = True
    StudentScheduleCourse s1 t1 == StudentScheduleCourse s2 t2 = s1 == s2
    _ == _ = False

createStudentScheduleCourse :: String -> StudentScheduleCourse
createStudentScheduleCourse sid = StudentScheduleCourse{studentId=sid, termCourses=[]}

addTermCourse :: StudentScheduleCourse -> TermCourse -> StudentScheduleCourse
addTermCourse studentSchedule termCourse = StudentScheduleCourse{studentId=(studentId studentSchedule), termCourses=courses}
    where
        courses = termCourse:(filter (==termCourse) (termCourses studentSchedule))

removeTermCourse :: StudentScheduleCourse -> String -> StudentScheduleCourse
removeTermCourse studentSchedule courseCode = StudentScheduleCourse{studentId=(studentId studentSchedule), termCourses=courses}
    where
        courses = filter (\course -> (code :: TermCourse -> String) course /= courseCode) (termCourses studentSchedule)

getTermCourses :: StudentScheduleCourse -> [TermCourse]
getTermCourses NullSchedule = []
getTermCourses (StudentScheduleCourse sid termCourses) = termCourses
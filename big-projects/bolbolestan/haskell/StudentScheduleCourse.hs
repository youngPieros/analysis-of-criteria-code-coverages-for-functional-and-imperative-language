module StudentScheduleCourse
( StudentScheduleCourse(..)
, createStudentScheduleCourse
, addTermCourse
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
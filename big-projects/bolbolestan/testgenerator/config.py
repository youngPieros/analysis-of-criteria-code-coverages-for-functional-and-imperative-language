class CommandsConfig:
    ADD_OFFERING = {'name': 'addOffering', 'number_of_random_offerings': 30}
    ADD_STUDENT = {'name': 'addStudent', 'number_of_new_students': 1000}
    GET_OFFERINGS = {'name': 'getOfferings'}
    GET_OFFERING = {'name': 'getOffering', 'number_of_get_offering_commands': 10}
    ADD_TO_WEEKLY_SCHEDULE = {'name': 'addToWeeklySchedule', 'number_of_students_take_term': 900}
    REMOVE_FROM_WEEKLY_SCHEDULE = {'name': 'removeFromWeeklySchedule', 'number_of_remove_schedules': 100}
    GET_WEEKLY_SCHEDULE = {'name': 'getWeeklySchedule'}
    FINALIZE = {'name': 'finalize'}


COURSE_PREFIX_NAMES = 'COURSE_'
INSTRUCTORS = ['Instructor_' + str(i + 1) for i in range(10)]
UNITS = [2, 3]
DAYS = {
    2: [['Sunday'], ['Monday'], ['Tuesday'], ['Wednesday'], ['Saturday']],
    3: [['Sunday', 'Tuesday'], ['Saturday', 'Monday'], ['Monday', 'Wednesday']]
}
CLASS_TIMES = ["7:3-9", "9-10:30", "10:30-12", "14-15:30", "16-17:30"]
EXAM_DATES = ["2022-1-0" + str(i) for i in range(1, 10)]
EXAM_TIMES = [
    ["08:00:00", "09:00:00"],
    ["09:00:00", "10:00:00"],
    ["10:00:00", "11:00:00"],
    ["11:00:00", "12:00:00"],
    ["08:00:00", "10:00:00"],
    ["09:00:00", "11:00:00"],
    ["10:00:00", "12:00:00"],
    ["08:00:00", "11:00:00"],
    ["09:00:00", "12:00:00"]
]
PREREQUISITES = ['PRE_COURSE_' + str(i + 1) for i in range(10)]
CAPACITIES = [1, 2]

STUDENT_PREFIX_NAMES = 'STUDENT_'
ENTERED_AT = [1396, 1397, 1398, 1399, 1400]
NOT_EXISTENCE_COURSE_CODE = '12345678'
NOT_EXISTENCE_STUDENT_ID = '012345678'



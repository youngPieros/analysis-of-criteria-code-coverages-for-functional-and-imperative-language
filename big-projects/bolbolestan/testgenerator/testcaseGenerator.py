import config
import random
import json


def select_random(array):
    return array[random.randint(0, len(array) - 1)]


class ExamTime:
    def __init__(self, start, end):
        self.start = start
        self.end = end


class ClassTime:
    def __init__(self, days=None, time=""):
        self.days = [] if days is None else days
        self.time = time


class Course:
    def __init__(self, code="", name="", instructor="", units=1, class_time=None, exam_time=None, capacity=0,
                 prerequisites=None):
        self.code = code
        self.name = name
        self.instructor = instructor
        self.units = units
        self.classTime = class_time
        self.examTime = exam_time
        self.capacity = capacity
        self.prerequisites = [] if prerequisites is None else prerequisites


class Student:
    def __init__(self, student_id="", name="", entered_at=1396):
        self.studentId = student_id
        self.name = name
        self.enteredAt = entered_at


class StudentCommandArgs:
    def __init__(self, student_id):
        self.studentId = student_id


class StudentOfferingCommandArgs:
    def __init__(self, student_id, code):
        self.studentId = student_id
        self.code = code


class Generator:
    courses = []
    students = []
    schedules = {}

    @staticmethod
    def generate_full_time_courses():
        pass

    @staticmethod
    def generate_random_exam_time():
        exam_time_range = select_random(config.EXAM_TIMES)
        exam_day = select_random(config.EXAM_DATES)
        [start, end] = [exam_day + "T" + t for t in exam_time_range]
        return ExamTime(start=start, end=end)

    @staticmethod
    def generate_random_class_time(units):
        days = select_random(config.DAYS[units])
        time = select_random(config.CLASS_TIMES)
        return ClassTime(days=days, time=time)

    @staticmethod
    def generate_new_random_course():
        code = ('00000000' + str(len(Generator.courses) + 1))[-8:]
        name = config.COURSE_PREFIX_NAMES + str(len(Generator.courses) + 1)
        instructor = select_random(config.INSTRUCTORS)
        units = select_random(config.UNITS)
        class_time = Generator.generate_random_class_time(units)
        exam_time = Generator.generate_random_exam_time()
        capacity = select_random(config.CAPACITIES)
        prerequisites = list(set([select_random(config.PREREQUISITES) for _ in range(random.randint(0, 2))]))
        course = Course(code=code, name=name, instructor=instructor, units=units, class_time=class_time,
                        exam_time=exam_time, capacity=capacity, prerequisites=prerequisites)
        Generator.courses.append(course)
        return course

    @staticmethod
    def generate_new_student():
        student_id = ('000000000' + str(len(Generator.students)))[-9:]
        name = config.STUDENT_PREFIX_NAMES + str(len(Generator.students) + 1)
        entered_at = select_random(config.ENTERED_AT)
        student = Student(student_id=student_id, name=name, entered_at=entered_at)
        Generator.students.append(student)
        return student

    @staticmethod
    def generate_add_weekly_scheduling(student, min_units):
        total_units = 0
        scheduled_courses = []
        if student.studentId not in Generator.schedules:
            Generator.schedules[student.studentId] = []
        while True:
            course = select_random(Generator.courses)
            total_units += course.units
            scheduled_course = StudentOfferingCommandArgs(student.studentId, course.code)
            scheduled_courses.append(scheduled_course)
            Generator.schedules[student.studentId].append(scheduled_course)
            if total_units >= min_units:
                break
        return scheduled_courses

    @staticmethod
    def generate_remove_weekly_scheduling(student_id):
        if student_id in Generator.schedules:
            scheduled_courses = Generator.schedules[student_id]
            removed_course = select_random(scheduled_courses)
            scheduled_courses = list(filter(lambda c: c.code != removed_course.code, scheduled_courses))
            Generator.schedules[student_id] = scheduled_courses
            return removed_course
        return None


class TestEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, ClassTime):
            return obj.__dict__
        elif isinstance(obj, ExamTime):
            return obj.__dict__
        elif isinstance(obj, Course):
            return obj.__dict__
        elif isinstance(obj, Student):
            return obj.__dict__
        elif isinstance(obj, StudentCommandArgs):
            return obj.__dict__
        elif isinstance(obj, StudentOfferingCommandArgs):
            return obj.__dict__
        return super().default(obj)


def dumps(obj):
    return json.dumps(obj, cls=TestEncoder)


def build_command(command_config, args):
    return command_config['name'] + ('' if args is None else ' ' + args)


def generate_add_courses_commands(command_config):
    commands = []
    for _ in range(command_config['number_of_random_offerings']):
        course = Generator.generate_new_random_course()
        commands.append(build_command(command_config, dumps(course)))
    return 2 * commands


def generate_add_student_commands(command_config):
    commands = []
    for _ in range(command_config['number_of_new_students']):
        student = Generator.generate_new_student()
        commands.append(build_command(command_config, dumps(student)))
    commands += commands[0:10]
    return commands


def generate_get_offerings_commands(config_command):
    student = select_random(Generator.students)
    commands = [build_command(config_command, dumps(StudentCommandArgs(student.studentId)))]
    commands += [build_command(config_command, dumps(StudentCommandArgs(config.NOT_EXISTENCE_COURSE_CODE)))]
    return commands


def generate_bad_commands(config_command):
    student = select_random(Generator.students)
    course = select_random(Generator.courses)
    null_student_arguments = StudentOfferingCommandArgs(config.NOT_EXISTENCE_STUDENT_ID, course.code)
    null_course_arguments = StudentOfferingCommandArgs(student.studentId, config.NOT_EXISTENCE_COURSE_CODE)
    command_arguments = [null_student_arguments, null_course_arguments]
    return [build_command(config_command, dumps(args)) for args in command_arguments]


def generate_get_offering_commands(config_command):
    commands = []
    for _ in range(config_command['number_of_get_offering_commands']):
        student = select_random(Generator.students)
        course = select_random(Generator.courses)
        args = StudentOfferingCommandArgs(student.studentId, course.code)
        commands.append(build_command(config_command, dumps(args)))
    commands += generate_bad_commands(config_command)
    return commands


def generate_add_weekly_schedule_courses(config_command):
    commands = []
    for student in Generator.students[0:config_command['number_of_students_take_term']]:
        min_units = random.randint(10, 22)
        scheduled_courses = Generator.generate_add_weekly_scheduling(student, min_units)
        commands += [build_command(config_command, dumps(scheduled_course)) for scheduled_course in scheduled_courses]
    commands += commands[0:10]
    commands += generate_bad_commands(config_command)
    return commands


def generate_remove_weekly_schedule_courses(config_command):
    commands = []
    for (student_id, _) in (list(Generator.schedules.items()))[0:config_command['number_of_remove_schedules']]:
        removed_course = Generator.generate_remove_weekly_scheduling(student_id)
        if not (removed_course is None):
            commands.append(build_command(config_command, dumps(removed_course)))
    commands *= 2
    commands += [build_command(config_command, dumps(StudentOfferingCommandArgs(student.studentId, select_random(Generator.courses).code))) for student in Generator.students[-10:]]
    commands += generate_bad_commands(config_command)
    return commands


def generate_get_weekly_schedule(command_config):
    commands = [build_command(command_config, dumps(StudentCommandArgs(student.studentId))) for student in
                Generator.students]
    commands.append(build_command(command_config, dumps(StudentCommandArgs(config.NOT_EXISTENCE_STUDENT_ID))))
    return commands


def generate_finalize_commands(command_config):
    commands = [build_command(command_config, dumps(StudentCommandArgs(student.studentId))) for student in Generator.students]
    commands.append(build_command(command_config, dumps(StudentCommandArgs(config.NOT_EXISTENCE_STUDENT_ID))))
    return commands


def generate_commands():
    commands = generate_add_courses_commands(config.CommandsConfig.ADD_OFFERING)
    commands += generate_add_student_commands(config.CommandsConfig.ADD_STUDENT)
    commands += generate_get_offerings_commands(config.CommandsConfig.GET_OFFERINGS)
    commands += generate_get_offering_commands(config.CommandsConfig.GET_OFFERING)
    commands += generate_get_weekly_schedule(config.CommandsConfig.GET_WEEKLY_SCHEDULE)
    commands += generate_add_weekly_schedule_courses(config.CommandsConfig.ADD_TO_WEEKLY_SCHEDULE)
    commands += generate_get_weekly_schedule(config.CommandsConfig.GET_WEEKLY_SCHEDULE)
    # commands += generate_remove_weekly_schedule_courses(config.CommandsConfig.REMOVE_FROM_WEEKLY_SCHEDULE)
    commands += generate_get_weekly_schedule(config.CommandsConfig.GET_WEEKLY_SCHEDULE)
    commands += generate_finalize_commands(config.CommandsConfig.FINALIZE)
    commands += generate_get_weekly_schedule(config.CommandsConfig.GET_WEEKLY_SCHEDULE)
    return commands


def main():
    for command in generate_commands():
        print(command)


main()

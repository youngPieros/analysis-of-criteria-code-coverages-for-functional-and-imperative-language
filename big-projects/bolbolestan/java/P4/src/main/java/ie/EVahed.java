package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EVahed
{
    private final ArrayList<Course> courses;
    private final ArrayList<Student> students;

    public EVahed() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }

    public String execCommand(String line) {
        ObjectMapper mapper = new ObjectMapper();

        String response = "";
        String command;
        String jsonData = "";

        if(line.indexOf(' ') != -1) {
            command = line.substring(0, line.indexOf(' '));
            jsonData = line.substring(line.indexOf(' ') + 1);
        }
        else {
            command = line;
        }
        try
        {
            switch (command)
            {
                case "addOffering":
                {
                    Course course;
                    jsonData = jsonData.replaceFirst("\"Instructor\"", "\"instructor\"");
                    try
                    {
                        course = mapper.readValue(jsonData, Course.class);
                        this.addCourse(course);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
                case "addStudent":
                {
                    Student student;
                    try
                    {
                        jsonData = jsonData.replaceFirst("\"StudentId\"", "\"studentId\"");
                        student = mapper.readValue(jsonData, Student.class);
                        for (Student registeredStudent : this.students)
                            if (registeredStudent.getStudentId().equals(student.getStudentId()))
                                throw new CustomException("Duplicate Student");
                        this.students.add(student);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
                case "getOfferings":
                {
                    Map<String, String> map = jsonParse(jsonData);
                    String studentId = map.get("StudentId");
                    findStudent(studentId);
                    ArrayList<CourseOfferingInfo> offeringsInfo = new ArrayList<>();
                    for (Course course: this.courses) {
                        CourseOfferingInfo offeringInfo = course.produceOfferingInfo();
                        offeringsInfo.add(offeringInfo);
                    }
                    try {
                        response = mapper.writeValueAsString(offeringsInfo);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "getOffering": {
                    Map<String, String> map = jsonParse(jsonData);
                    String code = map.get("code");
                    String studentId = map.get("StudentId");
                    findStudent(studentId);
                    Course course = findCourse(code);
                    try {
                        response = mapper.writeValueAsString(course);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "addToWeeklySchedule": {
                    Map<String, String> map = jsonParse(jsonData);
                    String code = map.get("code");
                    String studentId = map.get("StudentId");
                    Student student = findStudent(studentId);
                    Course course = findCourse(code);
                    student.addToWeeklySchedule(course);
                    break;
                }
                case "removeFromWeeklySchedule": {
                    Map<String, String> map = jsonParse(jsonData);
                    String code = map.get("code");
                    String studentId = map.get("StudentId");
                    Student student = findStudent(studentId);
                    Course course = findCourse(code);
                    student.removeToWeeklySchedule(course);
                    break;
                }
                case "getWeeklySchedule": {
                    Map<String, String> map = jsonParse(jsonData);
                    String studentId = map.get("StudentId");
                    Student student = findStudent(studentId);
                    try {
                        String weeklySchedule = mapper.writeValueAsString(student.getWeeklySchedule());
                        response = "{\"weeklySchedule\": " + weeklySchedule + "}";
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
//                    System.out.println(response);
                    break;
                }
                case "finalize": {
                    Map<String, String> map = jsonParse(jsonData);
                    String studentId = map.get("StudentId");
                    Student student = findStudent(studentId);
                    student.finalizeWeeklySchedule();
                    break;
                }
                default:
                    throw new CustomException("command not found");
            }

            try
            {
                return mapper.writeValueAsString(new BaseOutput(true, response));
            } catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
        }
        catch (CustomException e)
        {
            try
            {
                return mapper.writeValueAsString(new BaseOutput(false, e.getMessage()));
            } catch (JsonProcessingException jsonProcessingException)
            {
                jsonProcessingException.printStackTrace();
            }
        }
        return "";
    }

    private Student findStudent(String studentId) throws CustomException {
        for (Student student : this.students) {
            if (student.getStudentId().equals(studentId))
                return student;
        }
        throw new CustomException("StudentNotFound");
    }

    private Course findCourse(String code) throws CustomException {
        for (Course course : this.courses) {
            if (course.getCode().equals(code))
                return course;
        }
        throw new CustomException("OfferingNotFound");
    }

    private Map<String, String> jsonParse(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef =
                new TypeReference<HashMap<String, String>>() {};
        Map<String, String> map = null;
        try {
            map = mapper.readValue(jsonData, typeRef);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void addCourse(Course course) throws CustomException
    {
        for (int i = 0; i<this.courses.size(); i++)
            if (this.courses.get(i).getCode().equals(course.getCode()))
                throw new CustomException("Duplicate Course");

        for (int i = 0; i<this.courses.size(); i++) {
            if (this.courses.get(i).getName().equals(course.getName())) {
                this.courses.add(i,course);
                return;
            }
        }
        this.courses.add(course);
    }

    public void getCommands() {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();

            String response = execCommand(command);
            if(response.length() > 0)
                System.out.println(response);
        }
    }

}
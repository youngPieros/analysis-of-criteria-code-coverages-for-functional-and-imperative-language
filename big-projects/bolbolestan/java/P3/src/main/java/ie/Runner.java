package ie;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class Runner {
    public static void run() throws Exception {
        List<Student> students = new ArrayList<>();
        List<Offering> offerings = new ArrayList<>();

        // command line input scanner
        Scanner scanner = new Scanner(System.in);
        String command, commandName, commandObject;
        String[] commandParts;

        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            commandParts = command.split(" ", 2);
            commandName = commandParts[0];
            if (commandParts.length == 2)
                commandObject = commandParts[1];
            else commandObject = null;

            switch (commandName) {
                case "addOffering":
                    addOffering(commandObject, offerings); break;
                case "addStudent":
                    addStudent(commandObject, students); break;
                case "getOfferings":
                    getOfferings(commandObject, students, offerings); break;
                case "getOffering":
                    getOffering(commandObject, students, offerings); break;
                case "addToWeeklySchedule":
                    addToWeeklySchedule(commandObject, students, offerings); break;
                case "removeFromWeeklySchedule":
                    removeFromWeeklySchedule(commandObject, students, offerings); break;
                case "getWeeklySchedule":
                    getWeeklySchedule(commandObject, students, offerings); break;
                case "finalize":
                    finalize(commandObject, students, offerings); break;
                case "quit":
                    return;
                default:
                    printFailMessage("CommandNotFound");
            }
        }
    }

    private static void addOffering(String offeringJSON, List<Offering> offerings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Offering offering = objectMapper.readValue(offeringJSON, Offering.class);
        if (checkOffering(offering.code, offerings) == null) {
            offerings.add(offering);
            printSuccessMessage("\"\"");
        } else printFailMessage("OfferingAlreadyExists");
    }

    private static void addStudent(String studentJSON, List<Student> students) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(studentJSON, Student.class);
        if (checkStudent(student.studentId, students) == null) {
            students.add(student);
            printSuccessMessage("\"\"");
        } else printFailMessage("StudentAlreadyExists");
    }

    private static void getOfferings(String studentIdJSON, List<Student> students, List<Offering> offerings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = checkStudent(objectMapper.readTree(studentIdJSON).get("studentId").asText(), students);
        if (student == null) {
            printFailMessage("StudentNotFound");
            return;
        }

        // create json generator
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();
        JsonGenerator generator = factory.createGenerator(jsonObjectWriter);

        // show offerings sorted by course name
//        offerings.sort(Comparator.comparing(offerings));
        Collections.sort(offerings, new Comparator<Offering>(){
            public int compare(Offering o1, Offering o2){
                return o1.name.compareTo(o2.name);
            }
        });

        // generate a custom offering object
        generator.writeStartArray();
        for (Offering offering: offerings) {
            generator.writeStartObject();
            generator.writeStringField("code", offering.code);
            generator.writeStringField("name", offering.name);
            generator.writeStringField("instructor", offering.instructor);
            generator.writeEndObject();
        }
        generator.writeEndArray();
        generator.close();

        printSuccessMessage(jsonObjectWriter.toString());
    }

    private static void getOffering(String JSON, List<Student> students, List<Offering> offerings) throws IOException {
        String[] params = getParams(JSON); // params[0]: studentId, params[1]: code

        Student student = checkStudent(params[0], students);
        Offering offering = checkOffering(params[1], offerings);
        ObjectMapper objectMapper = new ObjectMapper();
        if (student == null) {
            printFailMessage("StudentNotFound");
        } else if (offering == null) {
            printFailMessage("OfferingNotFound");
        } else {
            printSuccessMessage(objectMapper.writeValueAsString(offering));
        }
    }

    private static void addToWeeklySchedule(String JSON, List<Student> students, List<Offering> offerings) throws IOException {
        String[] params = getParams(JSON); // params[0]: studentId, params[1]: code

        Student student = checkStudent(params[0], students);
        Offering offering = checkOffering(params[1], offerings);
        if (student == null) {
            printFailMessage("StudentNotFound");
        } else if (offering == null) {
            printFailMessage("OfferingNotFound");
        } else if (student.weeklySchedule.addNewCode(params[1])){
            printSuccessMessage("");
        } else printFailMessage("OfferingAlreadyExist");
    }

    private static void removeFromWeeklySchedule(String JSON, List<Student> students, List<Offering> offerings) throws IOException {
        String[] params = getParams(JSON); // params[0]: studentId, params[1]: code

        Student student = checkStudent(params[0], students);
        Offering offering = checkOffering(params[1], offerings);
        if (student == null) {
            printFailMessage("StudentNotFound");
        } else if (offering == null) {
            printFailMessage("OfferingNotFound");
        } else {
            if (student.weeklySchedule.codes.isEmpty()) {
                printFailMessage("OfferingNotFound");
            } else {
                student.weeklySchedule.removeCode(params[1]);
                printSuccessMessage("");
            }
        }
    }

    private static void getWeeklySchedule(String studentIdJSON, List<Student> students, List<Offering> offerings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = checkStudent(objectMapper.readTree(studentIdJSON).get("studentId").asText(), students);

        if (student == null) {
            printFailMessage("StudentNotFound");
        } else {
            // create json generator
            JsonFactory factory = new JsonFactory();
            StringWriter jsonObjectWriter = new StringWriter();
            JsonGenerator generator = factory.createGenerator(jsonObjectWriter);

            // generate a custom weekly schedule object
            generator.setCodec(new ObjectMapper());
            generator.writeStartObject();
            generator.writeFieldName("weeklySchedule");
            generator.writeStartArray();
            for (Map<String, String> map : student.weeklySchedule.codes) {
                Offering offering = checkOffering(map.get("code"), offerings);
                if (offering == null) {
                    printFailMessage("OfferingNotFound");
                    return;
                }
                generator.writeStartObject();
                generator.writeStringField("code", offering.code);
                generator.writeStringField("name", offering.name);
                generator.writeFieldName("classTime");
                generator.writeObject(offering.classTime);
                generator.writeFieldName("examTime");
                generator.writeObject(offering.examTime);
                generator.writeStringField("status", map.get("status"));
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.writeEndObject();
            generator.close();

            printSuccessMessage(jsonObjectWriter.toString());
        }
    }

    private static void finalize(String studentIdJSON, List<Student> students, List<Offering> offerings) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = checkStudent(objectMapper.readTree(studentIdJSON).get("studentId").asText(), students);

        if (student == null) {
            printFailMessage("StudentNotFound");
        } else {
            boolean finalizeStatus =
                    checkMinMaxUnits(student, offerings) &&
                    checkCapacity(student, offerings) &&
                    checkClassTimeCollision(student, offerings) &&
                    checkExamTimeCollision(student, offerings) &&
                    checkCapacity(student, offerings);

            if (finalizeStatus) {
                finalizeAllOfferings(student);
                reduceOfferingsCapacity(student, offerings);
                printSuccessMessage("");
            }
        }
    }

    private static boolean checkMinMaxUnits(Student student, List<Offering> offerings) {
        int totalUnits = 0;
        for (Map<String, String> map : student.weeklySchedule.codes) {
            Offering offering = checkOffering(map.get("code"), offerings);
            if (offering == null) {
                printFailMessage("OfferingNotFound");
                return false;
            } else {
                totalUnits += offering.units;
            }
        }
        if (totalUnits < 12) {
            printFailMessage("MinimumUnitsError");
            return false;
        } else if (totalUnits > 20) {
            printFailMessage("MaximumUnitsError");
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkClassTimeCollision(Student student, List<Offering> offerings) {
        try {
            for (Map<String, String> map1 : student.weeklySchedule.codes) {
                Offering offering1 = checkOffering(map1.get("code"), offerings);
                for (Map<String, String> map2 : student.weeklySchedule.codes) {
                    Offering offering2 = checkOffering(map2.get("code"), offerings);
                    if (offering1 != null && offering2 != null) {
                        if (!offering1.code.equals(offering2.code)) {
                            for (String day : offering1.classTime.days) {
                                if (Arrays.asList(offering2.classTime.days).contains(day)) {
                                    String[] times1 = offering1.classTime.time.split("-", 2);
                                    String[] times2 = offering2.classTime.time.split("-", 2);
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH[:mm]");
                                    LocalTime start1 = LocalTime.parse(times1[0], formatter);
                                    LocalTime end1 = LocalTime.parse(times1[1], formatter);
                                    LocalTime start2 = LocalTime.parse(times2[0], formatter);
                                    LocalTime end2 = LocalTime.parse(times2[1], formatter);
//                                System.out.println(start1.toString()+end1.toString()+start2.toString()+end2.toString());
                                    if ((start1.isBefore(end2) && start2.isBefore(end1))) {
                                        printFailMessage("ClassTimeCollisionError");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception ignored) {}
        return true;
    }

    private static boolean checkExamTimeCollision(Student student, List<Offering> offerings) {
        for (Map<String, String> map1 : student.weeklySchedule.codes) {
            Offering offering1 = checkOffering(map1.get("code"), offerings);
            for (Map<String, String> map2 : student.weeklySchedule.codes) {
                Offering offering2 = checkOffering(map2.get("code"), offerings);
                if (offering1 != null && offering2 != null) {
                    if (!offering1.code.equals(offering2.code)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        LocalDateTime start1 = LocalDateTime.parse(offering1.examTime.start, formatter);
                        LocalDateTime end1 = LocalDateTime.parse(offering1.examTime.end, formatter);
                        LocalDateTime start2 = LocalDateTime.parse(offering2.examTime.start, formatter);
                        LocalDateTime end2 = LocalDateTime.parse(offering2.examTime.end, formatter);
                        if (( start1.isBefore(end2) && start2.isBefore(end1))) {
                            printFailMessage("ExamTimeCollisionError");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static boolean checkCapacity(Student student, List<Offering> offerings) {
        for (Map<String, String> map : student.weeklySchedule.codes) {
            Offering offering = checkOffering(map.get("code"), offerings);
            if (offering == null) {
                printFailMessage("OfferingNotFound");
                return false;
            } else {
                if (offering.capacity == 0) {
                    printFailMessage("CapacityError" + " " + offering.code);
                    return false;
                }
            }
        }
        return true;
    }

    private static void finalizeAllOfferings(Student student) {
        for (Map<String, String> map : student.weeklySchedule.codes) {
            map.replace("status", "finalized");
        }
    }

    private static void reduceOfferingsCapacity(Student student, List<Offering> offerings) {
        for (Map<String, String> map : student.weeklySchedule.codes) {
            Offering offering = checkOffering(map.get("code"), offerings);
            if (offering != null) {
                offering.capacity -= 1;
            }
        }
    }

    private static Offering checkOffering(String code, List<Offering> offerings) {
        for (Offering offering : offerings) {
            if (offering.code.equals(code))
                return offering;
        }
        return null;
    }

    private static Student checkStudent(String studentId, List<Student> students) {
        for (Student student : students) {
            if (student.studentId.equals(studentId))
                return student;
        }
        return null;
    }

    private static boolean checkOfferingClassTimeAndInstructor(Offering offering, List<Offering> offerings) {
        for (Offering o : offerings) {
            if (offering.code.equals(o.code))
                if (offering.instructor.equals(o.instructor))
                    if (offering.classTime.time.equals(o.classTime.time))
                        if (Arrays.equals(offering.classTime.days, o.classTime.days))
                            return false;
        }
        return true;
    }

    private static String[] getParams(String JSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(JSON);
        String studentId = jsonNode.get("studentId").asText();
        String code = jsonNode.get("code").asText();
        return new String[] {studentId, code};
    }

    private static void printSuccessMessage(String responseData) {
        System.out.println("{\n\"success\" : true,\n\"data\" : " + responseData + "\n}");
    }

    private static void printFailMessage(String errorMessage) {
        System.out.println("{\n\"success\" : false,\n\"error\" : " + errorMessage + "\n}");
    }
}

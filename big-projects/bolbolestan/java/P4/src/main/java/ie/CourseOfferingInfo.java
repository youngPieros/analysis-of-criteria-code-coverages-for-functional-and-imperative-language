package ie;

public class CourseOfferingInfo {
    private String code;
    private String name;
    private String instructor;

    public CourseOfferingInfo(String code, String name, String instructor) {
        this.code = code; this.name = name; this.instructor = instructor;
    }

    public String getCode() {
        return code;
    }

    public String getName() { return name; }

    public String getInstructor() { return instructor; }
}

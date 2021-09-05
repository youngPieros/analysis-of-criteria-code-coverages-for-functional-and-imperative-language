package ie;

import java.util.ArrayList;

public class Course {

    private String code;
    private String name;
    private String instructor;
    private int units;
    private ClassTime classTime;
    private int capacity;
    private ArrayList<String> prerequisites;
    private TimeRange examTime;
    public int remainingCapacity;

    public void setCode(String code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public void setUnits(int units) {
        this.units = units;
    }
    public void setClassTime(ClassTime classTime) {
        this.classTime = classTime;
    }
    public void setCapacity(int capacity) { this.capacity = this.remainingCapacity = capacity; }
    public void setPrerequisites(ArrayList<String> prerequisites) {
        this.prerequisites = prerequisites;
    }
    public void setExamTime(TimeRange examTime) {
        this.examTime = examTime;
    }

    public String getName() { return this.name; }
    public String getCode() { return code; }
    public String getInstructor() { return instructor; }
    public int getUnits() { return units; }
    public ClassTime getClassTime() { return classTime; }
    public int getCapacity() { return capacity; }
    public ArrayList<String> getPrerequisites() { return prerequisites; }
    public TimeRange getExamTime() { return examTime; }

    public CourseOfferingInfo produceOfferingInfo() {
        return new CourseOfferingInfo(this.code, this.name, this.instructor);
    }

    public void decreaseCapacity() {
        this.remainingCapacity--;
    }

    public void increaseCapacity() {
        this.remainingCapacity++;
    }

    public void checkTimeConflicts(Course secondCourse) throws CustomException
    {
        String code1 = this.getCode();
        String code2 = secondCourse.getCode();
        if (Integer.parseInt(code1) > Integer.parseInt(code2))
        {
            String strtemp = code1;
            code1 = code2;
            code2 = strtemp;
        }
        if (this.classTime.hasConflict(secondCourse.getClassTime()))
            throw new CustomException("ClassTimeCollisionError " + code1 + " " + code2);
        if (this.examTime.hasConflict(secondCourse.getExamTime()))
            throw new CustomException("ExamTimeCollisionError " + code1 + " " + code2);
    }
}

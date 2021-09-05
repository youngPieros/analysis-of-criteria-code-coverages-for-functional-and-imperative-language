package ie;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Student {
    private String studentId;
    private String name;
    private String enteredAt;
    private HashMap<Course, FinalizedStatus> weeklyCourses= new HashMap<>();
    private Set<Course> removingCources = new HashSet<>();

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setEnteredAt(String enteredAt) { this.enteredAt = enteredAt; }

    public String getStudentId() { return studentId; }

    public void addToWeeklySchedule(Course course) throws CustomException {
        if (removingCources.contains(course))
        {
            removingCources.remove(course);
            return;
        }
        if (weeklyCourses.containsKey(course))
            throw new CustomException("Course already exists in weekly schedule");
        weeklyCourses.put(course, FinalizedStatus.NON_FINALIZED);
    }

    public void removeToWeeklySchedule(Course course) throws CustomException {
        if (!weeklyCourses.containsKey(course))
            throw new CustomException("Course not found in weekly schedule");
        removingCources.add(course);
    }

    public ArrayList<CourseWeeklyScheduleInfo> getWeeklySchedule() {
        ArrayList<CourseWeeklyScheduleInfo> result = new ArrayList<>();
        for (Course course: this.weeklyCourses.keySet()) {
            result.add(new CourseWeeklyScheduleInfo(course, this.weeklyCourses.get(course)));
        }
        for (Course course: this.removingCources) {
            if (this.weeklyCourses.get(course) == FinalizedStatus.FINALIZED)
                result.add(new CourseWeeklyScheduleInfo(course, this.weeklyCourses.get(course)));
        }
        return result;
    }

    public void finalizeWeeklySchedule() throws CustomException
    {
        int totalUnits = getWeeklyScheduleUnits();
        if (totalUnits < 12)
            throw new CustomException("MinimumUnitsError");
        if (totalUnits > 20)
            throw new CustomException("MaximumUnitsError");

        for (Course course: this.weeklyCourses.keySet())
            if (course.remainingCapacity == 0 && !this.removingCources.contains(course))
                throw new CustomException("CapacityError " + course.getCode());

        for (Course firstCourse: this.weeklyCourses.keySet())
            for (Course secondCourse: this.weeklyCourses.keySet())
                if (!firstCourse.getCode().equals(secondCourse.getCode()) &&
                        !this.removingCources.contains(firstCourse) && !this.removingCources.contains(secondCourse))
                {
                    firstCourse.checkTimeConflicts(secondCourse);
                }

        for (Course course : this.removingCources)
        {
            if (weeklyCourses.get(course) == FinalizedStatus.FINALIZED)
                course.increaseCapacity();
            weeklyCourses.remove(course);
        }

        this.removingCources.clear();

        for (Course course: this.weeklyCourses.keySet())
        {
            if (this.weeklyCourses.get(course) == FinalizedStatus.NON_FINALIZED)
            {
                this.weeklyCourses.replace(course, FinalizedStatus.FINALIZED);
                course.decreaseCapacity();
            }
        }
    }

    private int getWeeklyScheduleUnits()
    {
        int totalUnits = 0;
        for (Course course: this.weeklyCourses.keySet())
            totalUnits += course.getUnits();
        for(Course course : this.removingCources)
            totalUnits -= course.getUnits();
        return totalUnits;
    }
}

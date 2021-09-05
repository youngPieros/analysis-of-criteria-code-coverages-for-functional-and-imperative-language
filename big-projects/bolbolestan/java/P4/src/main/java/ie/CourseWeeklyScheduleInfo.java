package ie;

public class CourseWeeklyScheduleInfo {
    Course course;
    FinalizedStatus status;

    public CourseWeeklyScheduleInfo(Course course, FinalizedStatus status) { this.course = course; this.status = status; }

    public String getCode() { return this.course.getCode(); }
    public String getName() { return this.course.getName(); }
    public ClassTime getClassTime() { return this.course.getClassTime(); }
    public TimeRange getExamTime() { return this.course.getExamTime(); }
    public String getStatus() {
        if (this.status == FinalizedStatus.FINALIZED)
            return "finalized";
        else
            return "non-finalized";
    }
}

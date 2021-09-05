package ir.ut.student;

import org.json.JSONObject;

import ir.ut.exception.CapacityException;
import ir.ut.exception.ClassTimeCollisionException;
import ir.ut.exception.ExamTimeCollisionException;
import ir.ut.exception.MaximumUnitsException;
import ir.ut.exception.MinimumUnitsException;
import ir.ut.exception.OfferingNotInScheduleException;
import ir.ut.offering.Offering;

public class Student {
    private final String id;
    private final String name;
    private final String enteredAt;
    private final WeeklySchedule weeklySchedule = new WeeklySchedule();

    public Student(String id, String name, String enteredAt) {
        this.id = id;
        this.name = name;
        this.enteredAt = enteredAt;
    }

    public String getId() {
        return id;
    }

    public WeeklySchedule getWeeklySchedule() {
        return this.weeklySchedule;
    }

    public void addToWeeklySchedule(Offering offering) {
        this.weeklySchedule.addOfferingToSchedule(offering);
    }

    public void removeFromWeeklySchedule(Offering offering) throws OfferingNotInScheduleException {
        this.weeklySchedule.removeOfferingFromSchedule(offering);
    }

    public void finalizeSchedule() throws MinimumUnitsException, MaximumUnitsException, ClassTimeCollisionException,
        ExamTimeCollisionException, CapacityException {
        this.weeklySchedule.finalizeSchedule();
    }

    public static Student deserialize(JSONObject jsonObject) {
        return new Student(
            jsonObject.getString("studentId"),
            jsonObject.getString("name"),
            String.valueOf(jsonObject.getInt("enteredAt"))
        );
    }
}

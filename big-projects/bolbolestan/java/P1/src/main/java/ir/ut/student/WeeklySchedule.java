package ir.ut.student;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import ir.ut.exception.CapacityException;
import ir.ut.exception.ClassTimeCollisionException;
import ir.ut.exception.ExamTimeCollisionException;
import ir.ut.exception.MaximumUnitsException;
import ir.ut.exception.MinimumUnitsException;
import ir.ut.exception.OfferingNotInScheduleException;
import ir.ut.offering.Offering;

public class WeeklySchedule {
    private final HashMap<String, WeeklyScheduleOffering> offerings = new HashMap<>();

    public void addOfferingToSchedule(Offering offering) {
        WeeklyScheduleOffering result = this.offerings.get(offering.getCode());
        if (result == null) {
            this.offerings.put(offering.getCode(), new WeeklyScheduleOffering(offering));
        } else {
            result.setGoingToBeRemoved(false);
        }
    }

    public ArrayList<WeeklyScheduleOffering> getOfferings() {
        return new ArrayList<>(this.offerings.values());
    }

    public void removeOfferingFromSchedule(Offering offering) throws OfferingNotInScheduleException {
        WeeklyScheduleOffering result = this.offerings.get(offering.getCode());
        if (result == null) {
            throw new OfferingNotInScheduleException();
        }
        result.setGoingToBeRemoved(true);
    }

    public void finalizeSchedule() throws MinimumUnitsException, MaximumUnitsException, ClassTimeCollisionException,
        ExamTimeCollisionException, CapacityException {
        this.checkUnits();
        this.checkClassTimeCollision();
        this.checkExamTimeCollision();
        this.checkCapacity();
        this.finalizeAndRemoveOfferings();
    }

    private void checkUnits() throws MinimumUnitsException, MaximumUnitsException {
        int units = 0;
        for (WeeklyScheduleOffering weeklyScheduleOffering : this.offerings.values()) {
            if (!weeklyScheduleOffering.getIsGoingToBeRemoved()) {
                units += weeklyScheduleOffering.getOffering().getUnits();
            }
        }

        if (units < 12) {
            throw new MinimumUnitsException();
        }

        if (units > 20) {
            throw new MaximumUnitsException();
        }
    }

    private void checkClassTimeCollision() throws ClassTimeCollisionException {
        ArrayList<String> codes = new ArrayList<>(this.offerings.keySet());
        for (int i = 0; i < codes.size(); i++) {
            String code1 = codes.get(i);
            WeeklyScheduleOffering a = this.offerings.get(code1);
            if (a.getIsGoingToBeRemoved()) {
                continue;
            }

            for (int j = i + 1; j < codes.size(); j++) {
                String code2 = codes.get(j);
                WeeklyScheduleOffering b = this.offerings.get(code2);
                if (b.getIsGoingToBeRemoved()) {
                    continue;
                }

                if (a.getOffering().getClassTime().hasOerlap(b.getOffering().getClassTime())) {
                    throw new ClassTimeCollisionException(code1, code2);
                }
            }
        }
    }

    private void checkExamTimeCollision() throws ExamTimeCollisionException {
        ArrayList<String> codes = new ArrayList<>(this.offerings.keySet());
        for (int i = 0; i < codes.size(); i++) {
            String code1 = codes.get(i);
            WeeklyScheduleOffering a = this.offerings.get(code1);
            if (a.getIsGoingToBeRemoved()) {
                continue;
            }

            for (int j = i + 1; j < codes.size(); j++) {
                String code2 = codes.get(j);
                WeeklyScheduleOffering b = this.offerings.get(code2);
                if (b.getIsGoingToBeRemoved()) {
                    continue;
                }

                if (a.getOffering().getExamTime().hasOerlap(b.getOffering().getExamTime())) {
                    throw new ExamTimeCollisionException(code1, code2);
                }
            }
        }
    }

    private void checkCapacity() throws CapacityException {
        for (WeeklyScheduleOffering weeklyScheduleOffering : this.offerings.values()) {
            if (!weeklyScheduleOffering.getIsFinalized() && !weeklyScheduleOffering.getIsGoingToBeRemoved()) {
                Offering offering = weeklyScheduleOffering.getOffering();
                if (!offering.hasRemainingCapacity()) {
                    throw new CapacityException(offering.getCode());
                }
            }
        }
    }

    private void finalizeAndRemoveOfferings() {
        try {
            for (String code : this.offerings.keySet()) {
                WeeklyScheduleOffering weeklyScheduleOffering = this.offerings.get(code);
                if (weeklyScheduleOffering.getIsGoingToBeRemoved()) {
                    if (weeklyScheduleOffering.getIsFinalized()) {
                        weeklyScheduleOffering.getOffering().increaseRemainingCapacity();
                    }
                    this.offerings.remove(code);
                } else {
                    weeklyScheduleOffering.finalizeOffering();
                }
            }
        } catch (Exception ignored) {}
    }

    public JSONObject serialize() {
        ArrayList<JSONObject> serializedOfferings = new ArrayList<>();
        for (WeeklyScheduleOffering offering : this.offerings.values()) {
            serializedOfferings.add(offering.serialize());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("weeklySchedule", serializedOfferings);
        return jsonObject;
    }
}

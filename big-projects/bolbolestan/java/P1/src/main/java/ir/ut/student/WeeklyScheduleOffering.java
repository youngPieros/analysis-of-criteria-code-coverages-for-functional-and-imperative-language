package ir.ut.student;

import org.json.JSONObject;

import ir.ut.offering.Offering;

public class WeeklyScheduleOffering {
    private final Offering offering;
    private Boolean isGoingToBeRemoved = false;
    private Boolean isFinalized = false;

    public WeeklyScheduleOffering(Offering offering) {
        this.offering = offering;
    }

    public Offering getOffering() {
        return offering;
    }

    public Boolean getIsGoingToBeRemoved() {
        return this.isGoingToBeRemoved;
    }

    public Boolean getIsFinalized() {
        return this.isFinalized;
    }

    public void setGoingToBeRemoved(Boolean val) {
        this.isGoingToBeRemoved = val;
    }

    public void finalizeOffering() {
        if (!this.isFinalized) {
            this.offering.decreaseRemainingCapacity();
        }
        this.isFinalized = true;
    }

    public JSONObject serialize() {
        JSONObject result = this.offering.serialize();
        result.put("status", this.isFinalized ? "finalized" : "non-finalized");
        return result;
    }
}

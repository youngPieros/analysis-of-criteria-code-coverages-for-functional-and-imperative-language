package ir.ut.offering;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Offering {
    private String code;
    private String name;
    private String instructor;
    private int units;
    private ClassTime classTime;
    private ExamTime examTime;
    private int capacity;
    private int remainingCapacity;
    private ArrayList<String> prerequisites;

    public Offering(String code, String name, String instructor, int units, ClassTime classTime, ExamTime examTime,
            int capacity, ArrayList<String> prerequisites) {
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.units = units;
        this.classTime = classTime;
        this.examTime = examTime;
        this.capacity = capacity;
        this.remainingCapacity = capacity;
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public ClassTime getClassTime() {
        return this.classTime;
    }

    public ExamTime getExamTime() {
        return this.examTime;
    }

    public int getUnits() {
        return this.units;
    }

    public Boolean hasRemainingCapacity() {
        return this.remainingCapacity > 0;
    }

    public void increaseRemainingCapacity() {
        this.remainingCapacity++;
        if (this.remainingCapacity > this.capacity) {
            throw new RuntimeException("REMAINING CAPACITY OVER CAPACITY");
        }
    }

    public void decreaseRemainingCapacity() {
        this.remainingCapacity--;
        if (this.remainingCapacity < 0) {
            throw new RuntimeException("REMAINING CAPACITY NEGATIVE");
        }
    }

    public static Offering deserialize(JSONObject jsonObject) {
        ArrayList<String> prerequisites = new ArrayList<>();
        JSONArray jArray = (JSONArray) jsonObject.get("prerequisites");
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                prerequisites.add(jArray.getString(i));
            }
        }

        return new Offering(jsonObject.getString("code"), jsonObject.getString("name"),
                jsonObject.getString("instructor"), jsonObject.getInt("units"),
                ClassTime.deserialize(jsonObject.getJSONObject("classTime")),
                ExamTime.deserialize(jsonObject.getJSONObject("examTime")), jsonObject.getInt("capacity"),
                prerequisites);
    }



    public JSONObject serialize() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", this.code);
        jsonObject.put("name", this.name);
        jsonObject.put("instructor", this.instructor);
        jsonObject.put("units", this.units);
        jsonObject.put("capacity", this.capacity);
        jsonObject.put("prerequisites", this.prerequisites);
        jsonObject.put("classTime", this.classTime.serialize());
        jsonObject.put("examTime", this.examTime.serialize());

        return jsonObject;
    }

}

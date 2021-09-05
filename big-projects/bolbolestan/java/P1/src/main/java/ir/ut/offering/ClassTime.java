package ir.ut.offering;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClassTime {
    private ArrayList<String> days;
    private Time start;
    private Time end;

    public ClassTime(ArrayList<String> days, Time start, Time end) {
        this.days = days;
        this.start = start;
        this.end = end;
    }

    public static ClassTime deserialize(JSONObject jsonObject) {
        ArrayList<String> days = new ArrayList<>();
        JSONArray jArray = (JSONArray) jsonObject.get("days");
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                days.add(jArray.getString(i).toLowerCase());
            }
        }

        String[] parts = jsonObject.getString("time").split("-");
        String start = parts[0];
        String end = parts[1];

        return new ClassTime(
            days,
            Time.buildFromString(start),
            Time.buildFromString(end)
        );
    }

	public JSONObject serialize() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("days", this.days);
        jsonObject.put("time", String.format("%s-%s", this.start.serialize(), this.end.serialize()));

        return jsonObject;
	}

    public Boolean hasOerlap(ClassTime other) {
        if (this.start.isBefore(other.end) && other.start.isBefore(this.end)) {
            for (String day : this.days) {
                if (other.days.contains(day)) {
                    return true;
                }
            }
        }

        return false;
    }
}

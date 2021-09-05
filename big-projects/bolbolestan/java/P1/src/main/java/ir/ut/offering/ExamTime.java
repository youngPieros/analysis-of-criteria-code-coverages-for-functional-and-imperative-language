package ir.ut.offering;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class ExamTime {
    private Date start;
    private Date end;

    public ExamTime(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public static ExamTime deserialize(JSONObject jsonObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date start = new Date(), end = new Date();

        try {
            start = dateFormat.parse(jsonObject.getString("start"));
            end = dateFormat.parse(jsonObject.getString("end"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ExamTime(start, end);
    }

    public JSONObject serialize() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("start", dateFormat.format(this.start));
        jsonObject.put("end", dateFormat.format(this.end));

        return jsonObject;
	}

	public Boolean hasOerlap(ExamTime other) {
		return this.start.before(other.end) && other.start.before(this.end);
	}
}

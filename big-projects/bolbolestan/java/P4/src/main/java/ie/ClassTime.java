package ie;

import java.util.ArrayList;

public class ClassTime
{
    private ArrayList<String> days;
    private String time;

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getDays() { return days; }
    public String getTime() { return time; }

    private int convertToMinute(String hourMinutePair)
    {
        int startHour = 0;
        int startMinute = 0;
        int startIndex = hourMinutePair.indexOf(':');
        if (startIndex == -1)
        {
            startHour = Integer.parseInt(hourMinutePair);
        }
        else
        {
            String hourPart = hourMinutePair.substring(0, startIndex);
            startHour = Integer.parseInt(hourPart);
            String minutePart = hourMinutePair.substring(startIndex + 1);
            startMinute = Integer.parseInt(minutePart);
        }
        return (startHour * 60 + startMinute);
    }

    public boolean hasConflict(ClassTime other)
    {
        int start1, end1, start2, end2;
        int index = this.time.indexOf('-');
        start1 = this.convertToMinute(this.time.substring(0, index));
        end1 = this.convertToMinute(this.time.substring(index + 1));

        index = other.getTime().indexOf('-');
        start2 = this.convertToMinute(other.getTime().substring(0, index));
        end2 = this.convertToMinute(other.getTime().substring(index + 1));

        int startMax = Math.max(start1, start2);
        int endMin = Math.min(end1, end2);

        boolean timesHaveConflict = (startMax < endMin);

        for (int i=0;i<this.days.size();i++)
            for (int j=0;j<other.days.size();j++)
                if (this.days.get(i).equals(other.days.get(j)) && timesHaveConflict)
                    return true;
        return false;
    }
}

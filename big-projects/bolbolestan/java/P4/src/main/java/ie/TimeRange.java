package ie;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRange
{
    private String start;
    private String end;

    public void setStart(String start) {
        this.start = start;
    }
    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() { return start; }
    public String getEnd() { return end; }

    public boolean hasConflict(TimeRange other)
    {
        LocalDateTime start1 = LocalDateTime.parse(this.start, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end1 = LocalDateTime.parse(this.end, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime start2 = LocalDateTime.parse(other.start, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime end2 = LocalDateTime.parse(other.end, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LocalDateTime maxStart = start1;
        if (start2.isAfter(start1))
            maxStart = start2;

        LocalDateTime minEnd = end1;
        if (end2.isBefore(end1))
            minEnd = end2;

        return (maxStart.isBefore(minEnd));
    }
}

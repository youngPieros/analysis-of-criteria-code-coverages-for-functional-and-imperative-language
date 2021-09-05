package ir.ut.offering;

public class Time {
    int hour;
    int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static Time buildFromString(String time) {
        if (time.contains(":")) {
            String[] parts = time.split(":");
            return new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } else {
            return new Time(Integer.parseInt(time), 0);
        }
    }

    public String serialize() {
        if (this.minute == 0) {
            return String.format("%d", this.hour);
        }
        return String.format("%d:%d", this.hour, this.minute);
    }

    public Boolean isBefore(Time other) {
        if (this.hour == other.hour) {
            return this.minute < other.minute;
        }

        return this.hour < other.hour;
    }
}

package ir.ac.ut.ece.ie;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;

    public Location () {
        this.x = 0;
        this.y = 0;
    }

    public Location (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeLocation (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceFrom (Location location) {
        return Math.sqrt(Math.pow(location.x - this.x, 2) + Math.pow(location.y - this.y, 2));
    }

    public String toString () {
        return "x = " + this.x + " , y = " + this.y;
    }
}
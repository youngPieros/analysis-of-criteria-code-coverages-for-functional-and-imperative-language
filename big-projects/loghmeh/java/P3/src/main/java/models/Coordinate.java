package models;

public class Coordinate {
    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double findDistance(Coordinate loc){
        return Math.sqrt(Math.pow(this.x - loc.getX(), 2)+Math.pow(this.y - loc.getY(),2));
    }
}

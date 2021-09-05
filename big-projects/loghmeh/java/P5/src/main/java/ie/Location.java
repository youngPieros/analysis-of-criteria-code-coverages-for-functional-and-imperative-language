package ie;
public class Location {
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float sendDistance() {
        float distance = (float) Math.sqrt(x*x + y*y);
        return distance;
    }
}

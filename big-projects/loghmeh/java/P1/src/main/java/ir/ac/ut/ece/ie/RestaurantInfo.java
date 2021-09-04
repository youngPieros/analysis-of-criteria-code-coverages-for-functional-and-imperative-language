package ir.ac.ut.ece.ie;

import java.util.ArrayList;

public class RestaurantInfo {

    private String name;
    private String description;
    private Location location;
    private ArrayList <FoodInfo> menu;

    public RestaurantInfo () {
        this.name = "";
        this.description = "";
        this.location = new Location(0,0);
        this.menu = new ArrayList<FoodInfo>();
    }

    public String getName() {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<FoodInfo> getMenu() {
        return menu;
    }
    public void setMenu(ArrayList <FoodInfo> menu) {
        this.menu = menu;
    }

    public void addFood (FoodInfo newFood) {
        menu.add(newFood);
    }

    public double getPopularity (Location customerLocation) {
        double popularity = 0;
        double distance = customerLocation.getDistanceFrom(this.location);
        if (menu.size() == 0)
            return 0;
        if (distance == 0)
            return 100000000;
        for (FoodInfo foodInfo : menu)
            popularity += foodInfo.getPopularity();
        return popularity / menu.size() / distance;
    }
}
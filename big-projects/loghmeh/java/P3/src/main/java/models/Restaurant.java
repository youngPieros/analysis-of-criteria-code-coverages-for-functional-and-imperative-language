package models;

import java.util.List;

public class Restaurant {
    private String name;
    private String description;
    private Coordinate location;
    private List<Food> menu;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Coordinate getLocation() {
        return location;
    }

    public List<Food> getMenu() {
        return menu;
    }

    public void addFood(Food newFood){
        menu.add(newFood);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setMenu(List<Food> menu) {
        this.menu = menu;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }
}

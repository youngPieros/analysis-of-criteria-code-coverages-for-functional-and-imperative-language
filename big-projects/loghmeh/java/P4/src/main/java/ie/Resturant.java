package ie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Resturant {
    @JsonView(Views.RestaurantWithoutPopularity.class)
    @JsonProperty(required = true)
    private String name;
    @JsonView(Views.RestaurantWithoutPopularity.class)
    @JsonProperty(required = true)
    private String description;
    @JsonView(Views.RestaurantWithoutPopularity.class)
    @JsonProperty(required = true)
    private HashMap<String, Integer> location = new HashMap<String, Integer>();
    @JsonView(Views.RestaurantWithoutPopularity.class)
    @JsonProperty(required = true)
    private List<Food> menu = new ArrayList<Food>();

    private float popularity;
    private float averageFoodPopularity;
    private float distanceFromUser;

    static int NOT_FOUND = -1;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Food> getMenu() {
        return menu;
    }

    public HashMap<String, Integer> getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(HashMap<String, Integer> location) {

        this.location = location;
        distanceFromUser = (float)(Math.sqrt(Math.pow(location.get("x"),2) + Math.pow((location.get("y")), 2)));
    }

    public void setMenu(List<Food> menu) {
        this.menu = menu;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int findFoodIndex(String foodName){
        for(int i = 0; i < menu.size(); i++){
            if(menu.get(i).getName().equals(foodName))
                return i;
        }
        return NOT_FOUND;
    }

    public Interface.Status addFood(Food newFood){
        if(findFoodIndex(newFood.getName()) != NOT_FOUND){
            System.out.println("Food with name " + newFood.getName() + " in restaurant " + name + " already exist.");
            return Interface.Status.Fail;
        }
        menu.add(newFood);
        updatePopularity(newFood.getPopularity(), menu.size());
        return Interface.Status.Success;
    }

    public Food getFood(String foodName){
        int foodIndex = findFoodIndex(foodName);
        if(foodIndex == NOT_FOUND)
            return null;
        return menu.get(foodIndex);

    }

    public float calculateAverageOfFoodPopularity(){
        if(menu.size() == 0)
            return 0;
        float sum = 0;
        for (Food food : menu) sum += food.getPopularity();
        return sum / (float) menu.size();
    }

    public void calculatePopularity(){
        distanceFromUser = (float)(Math.sqrt(Math.pow(location.get("x"),2) + Math.pow((location.get("y")), 2)));
        averageFoodPopularity = calculateAverageOfFoodPopularity();
        if(distanceFromUser == 0)
            popularity = Float.POSITIVE_INFINITY;
        else
            popularity = averageFoodPopularity / distanceFromUser;
    }

    public void updatePopularity(float newFoodPopularity, int newSize){
        averageFoodPopularity = (averageFoodPopularity * (newSize - 1) + newFoodPopularity) / (float) newSize;
        if(distanceFromUser == 0)
            popularity = Float.POSITIVE_INFINITY;
        else
            popularity = averageFoodPopularity / distanceFromUser;
    }

    public void setRestaurantNameForFoods(){
        for (Food menu1 : menu) {
            if (menu1.getRestaurantName() == null)
                menu1.setRestaurantName(name);
        }
    }
}

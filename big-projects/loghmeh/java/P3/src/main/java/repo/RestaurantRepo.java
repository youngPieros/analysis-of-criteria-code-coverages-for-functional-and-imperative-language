package repo;


import models.Food;
import models.Restaurant;

import java.util.ArrayList;



public class RestaurantRepo {
    private static RestaurantRepo restaurantRepo;

    private ArrayList<Restaurant> restaurantList;


    public static RestaurantRepo getInstance(){
        if (restaurantRepo == null){
            restaurantRepo = new RestaurantRepo();
        }
        return restaurantRepo;
    }
    private RestaurantRepo(){
        restaurantList = new ArrayList<Restaurant>();
    }
    public void addRestaurant(Restaurant newRestaurant){
        restaurantList.add(newRestaurant);

    }

    public void addFood(Food newFood, String restaurantName){
        for(Restaurant r : restaurantList){
            if (r.getName().equals(restaurantName)){
                r.addFood(newFood);
            }
        }
    }
    public Restaurant searchRestaurant(String restaurantName){
        for(Restaurant r : restaurantList){
            if (r.getName().equals(restaurantName)){
                return r;
            }
        }
        return null;
    }

    public Food searchFood(String restaurantName, String foodName){
        Restaurant r = searchRestaurant(restaurantName);
        if(r == null){
            return null;
        }
        for(Food f : r.getMenu()){
            if (f.getName().equals(foodName))
                return f;
        }
        return null;
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
    }
}

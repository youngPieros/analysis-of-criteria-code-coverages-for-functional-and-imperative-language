package ie;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestaurantManager {

    private List<Restaurant> restaurants;

    public RestaurantManager() {
        restaurants = new ArrayList<Restaurant>();
    }

    public Restaurant findRestaurant(String name){
        for(int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(name)){
                return restaurants.get(i);
            }
        }
        return null;
    }

    public Food findFoodOfRestaurant(String restaurantName, String food){
        for(int i = 0; i < restaurants.size(); i++){
            if(restaurants.get(i).getName().equals(restaurantName)){
                return restaurants.get(i).findFood(food);
            }
        }
        return null;
    }

    public void addRestaurant(String restaurantJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Restaurant restaurant = mapper.readValue(restaurantJson, Restaurant.class);
        this.restaurants.add(restaurant);
    }

    public void printRestaurants(){
        int i;
        for(i = 0; i < restaurants.size() - 1; i++){
            System.out.print(restaurants.get(i).getName() + " | ");
        }
        System.out.print(restaurants.get(i).getName() + "\n");
    }

    public List<String> getRecommendedRestaurants(){
        ArrayList<Double> restaurantPopularities = new ArrayList<Double>();
        Map <Double,String> restaurant_popularity = new HashMap<Double, String>();
        List<String> recommendedRestaurants = new ArrayList<String>();

        for(int i = 0; i < restaurants.size(); i++){
            Restaurant Ri = restaurants.get(i);
            Double ratio = Ri.FoodPopularityAverage() / Ri.Distance();
            restaurantPopularities.add(ratio);
            restaurant_popularity.put(ratio,Ri.getName());
        }

        Collections.sort(restaurantPopularities);
        Collections.reverse(restaurantPopularities);
        if(restaurants.size() >= 1)
            recommendedRestaurants.add(restaurant_popularity.get(restaurantPopularities.get(0)));
        if(restaurants.size() >= 2)
            recommendedRestaurants.add(restaurant_popularity.get(restaurantPopularities.get(1)));
        if(restaurants.size() >= 3)
            recommendedRestaurants.add(restaurant_popularity.get(restaurantPopularities.get(2)));

        return recommendedRestaurants;

    }


}

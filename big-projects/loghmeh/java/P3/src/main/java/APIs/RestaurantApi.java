package APIs;

import models.DummyFood;
import models.Food;
import models.Restaurant;
import repo.RestaurantRepo;
import utils.JsonMapper;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantApi {

    private static RestaurantApi restaurantApi;
    private RestaurantRepo restaurantRepo;

    public static RestaurantApi getInstance() {
        if (restaurantApi == null){
            restaurantApi = new RestaurantApi();
        }
        return restaurantApi;
    }

    private RestaurantApi(){
        restaurantRepo = RestaurantRepo.getInstance();
    }

    public String  setRestaurantInfo(String jsonString){
        JsonMapper<Restaurant> restaurantJsonMapper = new JsonMapper<Restaurant>(Restaurant.class);
        Restaurant newRestaurant = restaurantJsonMapper.readJson(jsonString);
        restaurantRepo.addRestaurant(newRestaurant);
        return "Success!";
    }

    public String addNewFood(String jsonString){

        JsonMapper<DummyFood> foodInfoJsonMapper = new JsonMapper<DummyFood>(DummyFood.class);
        DummyFood newDummyFood  = foodInfoJsonMapper.readJson(jsonString);
        String restaurantName = newDummyFood.getRestaurantName();
        Food newFood = new Food(newDummyFood.getName(),newDummyFood.getDescription(),
                                newDummyFood.getPopularity(),newDummyFood.getPrice());
        restaurantRepo.addFood(newFood, restaurantName);
        return "Success!";
    }

    public String getRestaurantInfo(String jsonString){
        Map<String,String> restaurantNameInfo;
        JsonMapper<Map> restaurantInfoJsonMapper = new JsonMapper<>(Map.class);
        restaurantNameInfo = restaurantInfoJsonMapper.readJson(jsonString);
        String restaurantName = restaurantNameInfo.get("name");
        Restaurant r = restaurantRepo.searchRestaurant(restaurantName);
        if (r == null){
            return "Not Found!";
        }
        JsonMapper<Restaurant> restaurantJsonMapper = new JsonMapper<>(Restaurant.class);
        return restaurantJsonMapper.toJson(r);
    }

    public String getAllRestaurantNames(){
        ArrayList<Restaurant> restaurants = restaurantRepo.getRestaurantList();
        ArrayList<String> restaurantNames = new ArrayList<>();
        for(Restaurant r : restaurants){
            restaurantNames.add(r.getName());
        }
        JsonMapper<ArrayList> arrayListJsonMapper = new JsonMapper<>(ArrayList.class);
        return arrayListJsonMapper.toJson(restaurantNames);
    }
    public String getFoodInfo(String jsonString){
        JsonMapper<Map> mapJsonMapper = new JsonMapper<>(Map.class);
        Map<String,String> foodInfo = mapJsonMapper.readJson(jsonString);
        Food food = restaurantRepo.searchFood(foodInfo.get("restaurantName"), foodInfo.get("foodName"));
        if(food == null){
            return "Not Found!";
        }
        JsonMapper<Food> foodJsonMapper = new JsonMapper<>(Food.class);
        return foodJsonMapper.toJson(food);
    }


}

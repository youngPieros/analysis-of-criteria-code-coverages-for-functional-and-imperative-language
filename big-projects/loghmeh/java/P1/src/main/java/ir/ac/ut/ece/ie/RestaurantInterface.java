package ir.ac.ut.ece.ie;
import java.io.File;
import java.io.IOException;
import org.json.*;
import com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestaurantInterface {

    private RestaurantManager restaurantManager;

    public RestaurantInterface(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    public void getRestaurantsInterface () {
        restaurantManager.getRestaurants();
    }

    public void getRestaurantInterface (String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String name = jsonObject.getString("name");
        restaurantManager.getRestaurant(name);
    }

    public void addFoodToRestaurantInterface (String jsonString) {
        String restaurantName;
        JSONObject jsonObject = new JSONObject(jsonString);
        restaurantName = jsonObject.getString("restaurantName");
        FoodInfo foodInfo= new FoodInfo(jsonObject.getString("name" ) ,
                jsonObject.getString("description") ,
                jsonObject.getDouble("popularity"),
                jsonObject.getDouble("price"));
        try {
            restaurantManager.addFoodToRestaurant(restaurantName, foodInfo);
        }
        catch (JsonParseException e)
        {}
        catch (JsonMappingException e)
        {}
        catch (IOException e)
        {}
    }

    public void addRestaurantInterface(String jsonString){

        Gson gson = new Gson();
        RestaurantInfo restaurantInfo = gson.fromJson(jsonString, RestaurantInfo.class);
        try {
            restaurantManager.addRestaurant(restaurantInfo);
        }catch (JsonParseException e)
        {}
        catch (JsonMappingException e)
        {}
        catch (IOException e)
        {}
    }

    public void getFoodInterface (String jsonString) {
        String restaurantName;
        String foodName;
        JSONObject jsonObject = new JSONObject(jsonString);
        foodName = jsonObject.getString("foodName");
        restaurantName = jsonObject.getString("restaurantName");
        restaurantManager.getFood(restaurantName, foodName);
    }

    public void getRecommendedRestaurantsInterface () {
        restaurantManager.getRecommendedRestaurants();
    }

}
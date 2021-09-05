package ie;
import java.util.ArrayList;
import java.util.Scanner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.json.*;

import java.util.List;
import java.io.IOException;

public class Runner {
    private RestaurantManager manager;
    private Client client;
    public Runner() throws JsonParseException, JsonMappingException, IOException {
        manager = new RestaurantManager();
        client = new Client();
    }

    public Map<String,String> jsonToMap(String jsonStr) throws IOException{
        ObjectMapper mapper =  new ObjectMapper();
        Map<String,String> mappedJsonStr =  mapper.readValue(jsonStr,Map.class);
        return mappedJsonStr;
    }

    private void handleAddToCart(String jsonString)throws IOException{
        JSONObject basketJsonObject = new JSONObject(jsonString);
        Restaurant restaurant = manager.findRestaurant(basketJsonObject.getString("restaurantName"));
        if(restaurant ==  null)
            System.out.println("There is no restaurant with this name!");
        else{
            Map<String,String> uniqueFood_map = jsonToMap(jsonString);
            Food foundedUniqueFood = manager.findFoodOfRestaurant(uniqueFood_map.get("restaurantName"),uniqueFood_map.get("foodName"));
            if(foundedUniqueFood == null){
                System.out.println("There is no food with this name in this restaurant!");
            }
            else
                client.addToCart(jsonString);
        }
    }

    private void handleAddFood(String jsonString)throws IOException{
        JSONObject foodJsonObject = new JSONObject(jsonString);
        Restaurant foundedRestaurant = manager.findRestaurant(foodJsonObject.getString("restaurantName"));
        if(foundedRestaurant == null){
            System.out.println("There is no restaurant with this name!");
        }
        else{
            foodJsonObject.remove("restaurantName");
            ObjectMapper mapper = new ObjectMapper();
            Food food = mapper.readValue(foodJsonObject.toString(), Food.class);
            foundedRestaurant.addFood(food);
        }
    }

    private void handleGetRestaurant(String jsonString)throws IOException{
        Map<String,String> uniqueRestaurantName_map = jsonToMap(jsonString);
        Restaurant foundedUniqueRestaurant = manager.findRestaurant(uniqueRestaurantName_map.get("name"));
        if(foundedUniqueRestaurant == null){
            System.out.println("There is no restaurant with this name!");
        }
        else{
            ObjectMapper objectMapper = new ObjectMapper();
            String restaurantJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(foundedUniqueRestaurant);
            System.out.println(restaurantJsonStr);
        }
    }

    private void handleGetFood(String jsonString)throws  IOException{
        Map<String,String> uniqueFood_map = jsonToMap(jsonString);
        if(manager.findRestaurant(uniqueFood_map.get("restaurantName")) == null){
            System.out.println("There is no restaurant with this name!");
        }
        else {
            Food foundedUniqueFood = manager.findFoodOfRestaurant(uniqueFood_map.get("restaurantName"),uniqueFood_map.get("foodName"));
            if (foundedUniqueFood == null) {
                System.out.println("There is no food with this name in this restaurant!");
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                String foodJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(foundedUniqueFood);
                System.out.println(foodJsonStr);
            }
        }
    }

    private void handleRecommendedRestaurants(){
        List<String> recommendedRestaurants = manager.getRecommendedRestaurants();
        for(int i = 0; i < recommendedRestaurants.size(); i++){
            System.out.println(i + 1 + " : " + recommendedRestaurants.get(i) + " Restaurant");
        }
    }

    public void decideAct(String command) throws IOException,JSONException {
        String[] arrOfStr = command.split(" ",2);
        String action = arrOfStr[0];
        switch (action){
            case "addRestaurant":
                manager.addRestaurant( arrOfStr[1] );
                break;
            case "addFood":
                handleAddFood(arrOfStr[1]);
                break;
            case "getRestaurants":
                manager.printRestaurants();
                break;
            case "getRestaurant":
                handleGetRestaurant(arrOfStr[1]);
                break;
            case "getFood":
                handleGetFood(arrOfStr[1]);
                break;
            case "addToCart":
                handleAddToCart(arrOfStr[1]);
                break;
            case "getCart":
                client.showBasket();
                break;
            case "finalizeOrder":
                client.finalizeOrder();
                break;
            case "getRecommendedRestaurants":
                handleRecommendedRestaurants();

        }
    }

//    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//        Runner runner = new Runner();
//        while(true){
//            Scanner scanner = new Scanner(System.in);
//            String command = scanner.nextLine();
//            runner.decideAct(command);
//
//        }
//    }
}

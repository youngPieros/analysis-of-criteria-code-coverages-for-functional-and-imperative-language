package ie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Server {
    private ArrayList<Resturant> resturants;
    private Resturant[] mostPopularRestaurants;
    private User loginnedUser;
    static private ObjectMapper mapper;
    static private int NUM_OF_POPULAR_RESTAURANTS = 3;
    static private int NOT_FOUND = -1;

    public Server(){
        resturants = new ArrayList<Resturant>();
        loginnedUser = new User();
        mapper = new ObjectMapper();

    }

    static public Interface.Status printInfoOfObject(Object object, Class<?>  viewModel){
        try {
            String json;
            if(viewModel == null){
                json = mapper.writeValueAsString(object);
            }
            else {
                json = mapper.writerWithView(viewModel).writeValueAsString(object);
            }
            System.out.println(json);
            return Interface.Status.Success;
        } catch (JsonProcessingException e) {
            System.out.println("Exception in printInfoOfObject.");
            return Interface.Status.Fail;
        }
    }

    public Map<String,String> convertJsonToMap(String jsonString){
        try {
            return mapper.readValue(jsonString, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            return null;
        }
    }


    public boolean isDuplicatedRestaurantName(String name){
        for(int i = 0; i < resturants.size(); i++){
            if((resturants.get(i).getName()).equals(name))
                return true;
        }
        return false;
    }

    public boolean validMap(Map<String,String> map){
        if(map == null){
            System.out.println("Map in invalid. There was a problem in converting json to map.");
            return false;
        }
        return true;
    }

    public boolean validFields(Map<String,String> map, String[] fields){
        if(map.size() != fields.length){
            System.out.println("Number of field does not match to expected. expected:" + fields.length + "  but was:" + map.size());
            return false;
        }
        for(int i = 0; i < fields.length; i++){
            if(map.get(fields[i]) == null){
                System.out.println("Field with name " + fields[i] + " is missing.");
                return false;
            }
        }
        return true;
    }

    public Interface.Status addRestaurant(String restaurantInfo) {
        Resturant newRestaurant;
        try {
            newRestaurant = mapper.readValue(restaurantInfo, Resturant.class);
            if(isDuplicatedRestaurantName(newRestaurant.getName())){
                System.out.println("Restaurant with name " + newRestaurant.getName() + " already exist.");
                return Interface.Status.Fail;
            }
            newRestaurant.calculatePopularity();
            newRestaurant.setRestaurantNameForFoods();
            resturants.add(newRestaurant);
            updateRestaurantList(resturants.size() - 1);
            return Interface.Status.Success;
        } catch (Exception  e) {
            System.out.println("exception in addRestaurant");
            return Interface.Status.Fail;
        }

    }

    public int findRestaurantIndex(String restaurantName){
        for(int i = 0; i < resturants.size(); i++){
            if(resturants.get(i).getName().equals(restaurantName))
                return i;
        }
        return NOT_FOUND;
    }

    public Interface.Status addFood(String foodInfo){
        Food newFood;
        try {
            newFood = mapper.readValue(foodInfo, Food.class);
            int foodRestaurantIndex = findRestaurantIndex(newFood.getRestaurantName());
            if(foodRestaurantIndex == NOT_FOUND){
                System.out.println("Restaurant with name " + newFood.getRestaurantName() + " does not exist.");
                return Interface.Status.Fail;
            }
            if(resturants.get(foodRestaurantIndex).addFood(newFood) == Interface.Status.Fail)
                return Interface.Status.Fail;
            updateRestaurantList(foodRestaurantIndex);
            return Interface.Status.Success;

        } catch (Exception e) {
            System.out.println("exception in addFood");
            return Interface.Status.Fail;
        }
    }

    public ArrayList<String> getRestaurantsName(){
        ArrayList<String> restaurantsName = new ArrayList<String>();
        for(int i = 0; i < resturants.size(); i++)
            restaurantsName.add(resturants.get(i).getName());
        return restaurantsName;
    }

    public Interface.Status printRestaurantsName(){
        ArrayList<String> restaurantsName = getRestaurantsName();
        if(restaurantsName.size() == 0){
            System.out.println("There is no restaurants yet.");
            return Interface.Status.Success;
        }
        System.out.println("Name of restaurants are:");
        for(int i = 0; i < restaurantsName.size(); i++){
            System.out.println((i + 1) + "-" + restaurantsName.get(i));
        }
        return Interface.Status.Success;
    }

    public Interface.Status getRestaurant(String restaurantName){
        Map<String, String> restaurantNameMap = convertJsonToMap(restaurantName);
        if(!validMap(restaurantNameMap) || !validFields(restaurantNameMap, new String[]{"name"}))
            return Interface.Status.Fail;

        int resturantIndex = findRestaurantIndex(restaurantNameMap.get("name"));
        if(resturantIndex == NOT_FOUND){
            System.out.println("Restaurant with name " + restaurantNameMap.get("name") + " does not exist.");
            return Interface.Status.Fail;
        }

        return printInfoOfObject(resturants.get(resturantIndex), Views.RestaurantWithoutPopularity.class);
    }

    public Interface.Status getFood(String foodInfo){
        Map<String,String> foodInfoMap = convertJsonToMap(foodInfo);
        if(!validMap(foodInfoMap) || !validFields(foodInfoMap, new String[]{"restaurantName", "foodName"}))
            return Interface.Status.Fail;

        int foodRestaurantIndex = findRestaurantIndex(foodInfoMap.get("restaurantName"));
        if(foodRestaurantIndex == NOT_FOUND){
            System.out.println("Restaurant with name " + foodInfoMap.get("restaurantName") + " does not exist.");
            return Interface.Status.Fail;
        }

        Food foundFood = resturants.get(foodRestaurantIndex).getFood(foodInfoMap.get("foodName"));
        if(foundFood == null){
            System.out.println("Food with name " + foodInfoMap.get("foodName") + " in restaurant " + foodInfoMap.get("restaurantName") +
                               " does not exist.");
            return Interface.Status.Fail;
        }

        return printInfoOfObject(foundFood, Views.FoodWithoutRestaurantName.class);
    }

    public int findProperIndexToAdd(float newPopularity){
        for(int i = 0; i < resturants.size(); i++){
            if(resturants.get(i).getPopularity() < newPopularity)
                return i;
        }
        return resturants.size();
    }

    public void updateRestaurantList(int restaurantIndex){
        Resturant updatedRestaurant = resturants.get(restaurantIndex);
        float newPopularity = updatedRestaurant.getPopularity();
        resturants.remove(restaurantIndex);
        int newIndex = findProperIndexToAdd(newPopularity);
        resturants.add(newIndex, updatedRestaurant);
    }

    public Interface.Status addToCart(String foodInfo){
        Map<String,String> foodInfoMap = convertJsonToMap(foodInfo);
        if(!validMap(foodInfoMap) || !validFields(foodInfoMap, new String[]{"restaurantName", "foodName"}))
            return Interface.Status.Fail;
        int foodRestaurantIndex = findRestaurantIndex(foodInfoMap.get("restaurantName"));
        if(foodRestaurantIndex == NOT_FOUND){
            System.out.println("Restaurant with name " + foodInfoMap.get("restaurantName") + " does not exist.");
            return Interface.Status.Fail;
        }

        Food order = resturants.get(foodRestaurantIndex).getFood(foodInfoMap.get("foodName"));
        if(order == null){
            System.out.println("Food with name " + foodInfoMap.get("foodName") + "in restaurant " + foodInfoMap.get("restaurantName") +
                    " does not exist.");
            return Interface.Status.Fail;
        }

        return loginnedUser.addToCart(resturants.get(foodRestaurantIndex), order);
    }

    public Interface.Status getCart(){
        return loginnedUser.printCart();
    }

    public Interface.Status finalizeOrder(){
        return loginnedUser.finalizeOrder();
    }

    public ArrayList<String> getMostPopularRestaurants(){
        ArrayList<String> mostPopularRestaurants = new ArrayList<String>();
        for(int i = 0; i < Math.min(NUM_OF_POPULAR_RESTAURANTS, resturants.size()); i++)
            mostPopularRestaurants.add(resturants.get(i).getName());
        return mostPopularRestaurants;
    }

    public Interface.Status getRecommendedRestaurants(){
        ArrayList<String> mostPopularRestaurants = getMostPopularRestaurants();
        for(int i = 0; i < mostPopularRestaurants.size(); i++){
            System.out.println((i + 1) + "-" + mostPopularRestaurants.get(i));
        }
        return Interface.Status.Success;
    }

}

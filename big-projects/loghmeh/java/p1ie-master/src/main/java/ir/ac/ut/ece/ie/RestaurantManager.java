package ir.ac.ut.ece.ie;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class RestaurantManager {
    private ArrayList <RestaurantInfo> restaurants;
    private Gson gson;
    private File file;
    private ObjectMapper mapper;
    private int TOP_POPULAR_RESTAURANT_NUMBER = 3;

    public RestaurantManager(String fileName) throws JsonParseException, JsonMappingException, IOException {
        restaurants = new ArrayList<RestaurantInfo>();
        this.gson = new Gson();
        file = new File(fileName);
        mapper = new ObjectMapper();
        restaurants = mapper.readValue (file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, RestaurantInfo.class));
    }

    public void getRestaurants()
    {
        for (RestaurantInfo restaurantInfo : restaurants)
            System.out.println(restaurantInfo.getName());
    }

    public void getRestaurant(String name)
    {
        for (RestaurantInfo restaurantInfo : restaurants)
            if (restaurantInfo.getName().equals(name)) {
                System.out.println(gson.toJson(restaurantInfo));
                return;
            }
        System.out.println("there is not " + name + " restaurant");
    }

    public void addFoodToRestaurant (String restaurantName, FoodInfo foodInfo) throws JsonParseException, JsonMappingException, IOException {
        for (RestaurantInfo restaurantInfo : restaurants)
            if (restaurantInfo.getName().equals(restaurantName)) {
                for (FoodInfo food : restaurantInfo.getMenu())
                    if (food.getName().equals(foodInfo.getName())) {
                        System.out.println("there is " + foodInfo.getName() + " already in " + restaurantName);
                        return;
                    }
                restaurantInfo.addFood(foodInfo);
                mapper.writeValue(file, restaurants);
                return;
            }
        System.out.println("there is not " + restaurantName + " restaurant");
    }

    public void addRestaurant(RestaurantInfo restaurantInfo) throws JsonParseException, JsonMappingException, IOException{
        for (RestaurantInfo restaurant : restaurants)
            if (restaurant.getName().equals(restaurantInfo.getName())) {
                System.out.println("there is " + restaurantInfo.getName() + " already in restaurants");
                return;
            }
        restaurants.add(restaurantInfo);
        mapper.writeValue(file, restaurants);
    }

    public FoodInfo findFood (String restaurantName, String foodName) {
        for (RestaurantInfo restaurantInfo : restaurants)
            if (restaurantInfo.getName().equals(restaurantName)) {
                for (FoodInfo foodInfo : restaurantInfo.getMenu())
                    if (foodInfo.getName().equals(foodName))
                        return foodInfo;
                return null;
            }
        return null;
    }

    public void getFood (String restaurantName, String foodName) {
        FoodInfo foodInfo = findFood(restaurantName, foodName);
        if (foodInfo == null)
            System.out.println("there is not this food");
        else
            System.out.println(gson.toJson(foodInfo));
    }

    public void getRecommendedRestaurants () {
        int top = this.TOP_POPULAR_RESTAURANT_NUMBER;
        String [] popularRestaurants = new String[top+1];
        double [] popularityList = new double[top+1];
        int listCounter;
        for (RestaurantInfo restaurantInfo : restaurants) {
            popularityList[top] = restaurantInfo.getPopularity(new Location(0, 0));
            popularRestaurants[top] = restaurantInfo.getName();
            listCounter = top;
            while (listCounter != 0 && popularityList[listCounter] >= popularityList[listCounter-1]) {
                String tempRestaurant = popularRestaurants[listCounter];
                double tempPopularity = popularityList[listCounter];
                popularRestaurants[listCounter] = popularRestaurants[listCounter-1];
                popularRestaurants[listCounter-1] = tempRestaurant;
                popularityList[listCounter] = popularityList[listCounter-1];
                popularityList[listCounter-1] = tempPopularity;
                listCounter--;
            }
        }
        for (int i = 0; i < top; i++)
            if (popularRestaurants[i] == null || popularRestaurants[i].equals(""))
                return;
            else
                System.out.println(popularRestaurants[i]);
    }

}

// commands test:
/*
    getRestaurant {"name": "Hesturan"}
    addRestaurant {"name":"Iman","description":"Iman Restaurant","location":{"x":7,"y":13},"menu":[{"name":"Ghormeh Sabzi","description":"it’s yummy!","popularity":0.99,"price":35000},{"name":"Kabab","description":"it’s so delicious!","popularity":0.7,"price":45000}]}
    getRestaurant {"name" : "Iman"}
    getRestaurants
    getRecommendedRestaurants

    addRestaurant {"name":"Nastaran","description":"Nastaran Restaurant","location":{"x":13,"y":7},"menu":[{"name":"felafel","description":"it’s yummy!","popularity":0.8,"price":12000},{"name":"Joojeh Kabab","description":"it’s wonderful","popularity":0.9,"price":52000}]}
    getRestaurant {"name" : "Nastaran"}
    getRestaurants
    getRecommendedRestaurants

    addRestaurant {"name":"Meykhosh","description":"MeyKhosh fantastic","location":{"x":1,"y":3},"menu":[]}
    getRestaurant {"name" : "Meykhosh"}
    getRestaurants
    getRecommendedRestaurants

    addFood {"restaurantName": "Iman", "name": "soup", "description": "tasty soup", "popularity": 0.6, "price": 27000}
    addFood {"restaurantName": "Nastaran", "name": "soup", "description": "not tasty soup", "popularity": 0.4, "price": 20000}
    addFood {"restaurantName": "Meykhosh", "name": "peperoni pizza", "description": "hot peperoni pizza", "popularity": 0.95, "price": 45000}
    getRestaurant {"name" : "Iman"}
    getRestaurant {"name" : "Nastaran"}
    getRestaurant {"name" : "Meykhosh"}
    getRestaurant {"name" : "akbarjoojeh"}

    getFood {"foodName" : "Kabab", "restaurantName": "Hesturan"}
    getFood {"foodName" : "soup", "restaurantName": "Iman"}
    getFood {"foodName" : "soup", "restaurantName": "Nastaran"}
    getFood {"foodName" : "Joojeh Kabab", "restaurantName": "Nastaran"}
    getFood {"foodName" : "peperoni pizza", "restaurantName": "Meykhosh"}
    getFood {"foodName" : "Ghormeh Sabzi", "restaurantName": "Iman"}
    getFood {"foodName" : "nokhod siah", "restaurantName": "Iman"}
    getFood {"foodName" : "nokhod siah", "restaurantName": "havijRestaurant"}

    getCart
    addToCart {"foodName" : "Joojeh Kabab", "restaurantName": "Nastaran"}
    addToCart {"foodName" : "Ghormeh Sabzi", "restaurantName": "Iman"}
    addToCart {"foodName" : "peperoni pizza", "restaurantName": "Meykhosh"}
    getCart
    addToCart {"foodName" : "soup", "restaurantName": "Nastaran"}
    getCart
    addToCart {"foodName" : "felafel", "restaurantName": "Nastaran"}
    getCart
    addToCart {"foodName" : "Joojeh Kabab", "restaurantName": "Nastaran"}
    getCart
    addToCart {"foodName" : "Joojeh Kabab", "restaurantName": "Nastaran"}
    addToCart {"foodName" : "Ghormeh Sabzi", "restaurantName": "Iman"}
    getCart
    addToCart {"foodName" : "adas polo", "restaurantName": "Iman"}
    addToCart {"foodName" : "shivid polo", "restaurantName": "Nastaran"}
    finalizeOrder

    getRecommendedRestaurants



 */

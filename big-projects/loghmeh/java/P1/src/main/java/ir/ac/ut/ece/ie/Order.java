package ir.ac.ut.ece.ie;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Order {

    private RestaurantManager restaurantManager;
    private String restaurantName;
    private Map <String, Integer> orderedFoods;

    public Order (RestaurantManager restaurantManager) throws JsonParseException, JsonMappingException, IOException {
        this.restaurantManager = restaurantManager;
        this.orderedFoods = new HashMap <String, Integer>();
        this.restaurantName = "";
    }

    public void addToCart (String restaurantName, String foodName) {
        if (!this.restaurantName.equals("") && !this.restaurantName.equals(restaurantName)) {
            System.out.println("you can't order food from different restaurants");
            return;
        }
        FoodInfo newOrder = restaurantManager.findFood(restaurantName, foodName);
        if (newOrder == null)
            System.out.println("there is not this order");
        else {
            if (orderedFoods.containsKey(newOrder.getName()))
                orderedFoods.replace(newOrder.getName(), orderedFoods.get(newOrder.getName()) + 1);
            else
                orderedFoods.put(newOrder.getName(), 1);
            this.restaurantName = restaurantName;
        }
    }

    public void getCart() {
        if (orderedFoods.isEmpty())
            System.out.println("empty order!");
        for (Map.Entry<String, Integer> entry : orderedFoods.entrySet())
            System.out.println("foodName : " + entry.getKey() + ", count: " + entry.getValue());

    }

    public void finalizeOrder () {
        if (orderedFoods.isEmpty()) {
            System.out.println("there is no order");
            return;
        }
        getCart ();
        System.out.println("Your order has been successfully registered");
        this.restaurantName = "";
        this.orderedFoods.clear();
    }

}

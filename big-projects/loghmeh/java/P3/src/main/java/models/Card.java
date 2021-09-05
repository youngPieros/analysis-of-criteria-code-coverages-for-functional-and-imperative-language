package models;

import java.util.HashMap;
import java.util.Map;

public class Card {
    private Map<String,Integer> shoppingList;
    private String restaurantName;
    public Card(){
        shoppingList = new HashMap<>();
    }
    public boolean addItem(String restaurantName,String foodName){
        if (this.restaurantName == null){
            this.restaurantName = restaurantName;
        }
        if(this.restaurantName.equals(restaurantName)){
            if(shoppingList.containsKey(foodName)){
                int itemCount = shoppingList.get(foodName);
                itemCount+=1;
                shoppingList.replace(foodName,itemCount);
            }
            else{
                shoppingList.put(foodName,1);
            }
            return true;
        }
        else return false;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Map<String, Integer> getShoppingList() {
        return shoppingList;
    }
}

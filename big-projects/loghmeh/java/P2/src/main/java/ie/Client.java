package ie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private Map<String, Integer> basket;
    private String restaurantName;
    public Client(){
        basket = new HashMap<String, Integer>();
    }

    public void addToCart(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> info = mapper.readValue(jsonString, Map.class);
        String resName = info.get("restaurantName");
        String foodName = info.get("foodName");
        if(basket.isEmpty()){
            restaurantName = resName;
            basket.put(foodName,1);
        }
        else {
            if (!resName.equals(restaurantName)) {
                System.out.println("your order is from another restaurant!");
            } else {
                if (basket.containsKey(foodName)) {
                    basket.put(foodName, basket.get(foodName) + 1);
                } else {
                    basket.put(foodName, 1);
                }
            }
        }
    }


    public void showBasket()throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(basket);
        System.out.println(json);
    }

    public void finalizeOrder()throws IOException{
        showBasket();
        System.out.println("Your orders have been recorded!");
        basket.clear();
    }
}


package ie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String restaurantName;
    private ArrayList<Order> orders = new ArrayList<Order>();

    private static int NOT_FOUND = -1;

    public Cart(){
        restaurantName = null;
    }

    public int fineOrderIndex(String foodName){
        for(int i = 0; i < orders.size(); i++){
            if(orders.get(i).getFoodName().equals(foodName))
                return i;
        }
        return NOT_FOUND;
    }

    public Interface.Status addOrder(Resturant resturant, Food food){
        if(restaurantName == null)
            restaurantName = resturant.getName();
        else if(!resturant.getName().equals(restaurantName)){
            System.out.println("You can not order from two different restaurant");
            return Interface.Status.Fail;
        }
        int orderIndex = fineOrderIndex(food.getName());
        if(orderIndex == NOT_FOUND)
            orders.add(new Order(food, 1));
        else
            orders.get(orderIndex).addNumOfFoods(1);

        return Interface.Status.Success;
    }

    public ArrayList<HashMap<String, Object>> getOrdersProperties(){
        ArrayList<HashMap<String,Object>> ordersProperties = new ArrayList<HashMap<String, Object>>();

        for (Order order : orders) {
            HashMap<String, Object> orderProperites = new HashMap<String, Object>();
            orderProperites.put("foodName", order.getFoodName());
            orderProperites.put("numOfFood", order.getNumOfFoods());
            ordersProperties.add(orderProperites);
        }
        return ordersProperties;
    }

    public Interface.Status printCart(){//TODO check for when there is no order
        ArrayList<HashMap<String, Object>> ordersProperties = getOrdersProperties();
        if(orders.size() == 0){
            System.out.println("There is no order to show.");
            return Interface.Status.Success;
        }

        System.out.println("orders are as below:");
        for (HashMap<String, Object> ordersProperty : ordersProperties) {
            Server.printInfoOfObject(ordersProperty, null);
        }

        return Interface.Status.Success;

    }

    public Interface.Status finalizeOrder(){//TODO check for when there is no order
        if(printCart() == Interface.Status.Fail)
            return Interface.Status.Fail;

        if(orders.size() != 0)
            System.out.println("Orders were finalized.");

        restaurantName = null;
        orders.clear();

        return Interface.Status.Success;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}

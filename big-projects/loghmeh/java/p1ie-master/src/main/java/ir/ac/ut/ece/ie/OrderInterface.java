package ir.ac.ut.ece.ie;
import org.json.*;

public class OrderInterface {
    private Order order;

    public OrderInterface (Order order) {
        this.order = order;
    }

    public void addToCartInterface(String jsonString){
        String restaurantName;
        String foodName;
        JSONObject jsonObject = new JSONObject(jsonString);
        foodName = jsonObject.getString("foodName");
        restaurantName = jsonObject.getString("restaurantName");
        order.addToCart(restaurantName, foodName);
    }

    public void getCartInterface () {
        order.getCart();
    }

    public void finalizeOrderInterface () {
        order.finalizeOrder();
    }
}
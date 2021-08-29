package ir.ac.ut.ece.ie;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class OrderManager {
    private String commandName;
    private String jsonData;
    private RestaurantInterface restaurantInterface;
    private OrderInterface orderInterface;
    private Order order;
    private RestaurantManager restaurantManager;

    public OrderManager(String fileName) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.restaurantManager = new RestaurantManager(fileName);
        this.restaurantInterface = new RestaurantInterface(restaurantManager);
        this.order = new Order (restaurantManager);
        this.orderInterface = new OrderInterface(order);
    }

    public void separate(String command) {
        boolean hasCommandJsonData = false;
        if (command.contains(" "))
            hasCommandJsonData = true;
        if (hasCommandJsonData) {
            String[] arr = command.split(" ", 2);
            this.commandName = arr[0];
            this.jsonData = arr[1];
        }
        else {
            this.commandName = command;
            this.jsonData = "";
        }
        System.out.println("command type : " + commandName);
        System.out.println("json command : *" + jsonData + "*");
    }

    public void runCommand (){
        switch (commandName){

            case "addRestaurant":
                restaurantInterface.addRestaurantInterface(jsonData);
                break;

            case "addFood":
                restaurantInterface.addFoodToRestaurantInterface(jsonData);
                break;

            case "getRestaurants":
                restaurantInterface.getRestaurantsInterface();
                break;

            case "getRestaurant":
                restaurantInterface.getRestaurantInterface(jsonData);
                break;

            case "getFood":
                restaurantInterface.getFoodInterface(jsonData);
                break;

            case "addToCart":
                orderInterface.addToCartInterface(jsonData);
                break;

            case "getCart":
                orderInterface.getCartInterface();
                break;

            case "finalizeOrder":
                orderInterface.finalizeOrderInterface();
                break;

            case "getRecommendedRestaurants":
                restaurantInterface.getRecommendedRestaurantsInterface();
                break;
        }

    }


    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        OrderManager orderManager = new OrderManager( "src/main/resources/restaurants.json");
        Scanner inputScanner = new Scanner(System.in);
        String command;

        while (inputScanner.hasNextLine()) {
            command = inputScanner.nextLine();
            orderManager.separate(command);
            orderManager.runCommand();
        }
    }
}

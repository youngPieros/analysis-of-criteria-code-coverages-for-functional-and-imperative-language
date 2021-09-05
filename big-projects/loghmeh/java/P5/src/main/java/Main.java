import ie.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private static App app;
    public static void handleAction(String action, String jsonData) throws IOException {
        try {
            switch (action) {
                case "addRestaurant":
                    app.addRestaurant(jsonData);
                    break;
                case "addFood":
                    app.addFood(jsonData);
                    break;
                case "getRestaurants":
                    System.out.print(app.getRestaurantsInfo());
                    break;
                case "getRestaurant":
                    System.out.println(app.getRestaurant(jsonData));
                    break;
                case "getFood":
                    System.out.println(app.getFood(jsonData));
                    break;
                case "addToCart":
                    app.addToCart(jsonData);
                    break;
                case "getCart":
                    System.out.println(app.getCartJson());
                    break;
                case "finalizeOrder":
                    System.out.println(app.finalizeOrder());
                    System.out.println("Your order was submitted.");
                    break;
                case "getRecommendedRestaurants":
                    System.out.println(app.getRecommendedRestaurants());
                    break;
                default:
                    System.out.println("Wrong command!");
                    break;
            }
        }
        catch (Exception e) {
            if (e instanceof FoodNotFoundExp)
                System.out.println("Food does not exist!");
            else if (e instanceof RestaurantNotFoundExp)
                System.out.println("Restaurant does not exist!");
            else if (e instanceof FoodFromOtherRestaurantInCartExp)
                System.out.println("There is food from another restaurant in your cart!");
            else if (e instanceof FoodAlreadyExistsExp)
                System.out.println("Food already exists!");
            else if (e instanceof RestaurantAlreadyExistsExp)
                System.out.println("Restaurant already exists!");
            else
                System.out.println("Bad input!");
        }
    }

    public static void main(String[] args) throws IOException {
        app = App.getInstance();
        Scanner inputScanner = new Scanner(System.in);

        while (inputScanner.hasNextLine()) {
            String command = inputScanner.nextLine();
            String action = "";
            String jsonData = "";
            if(command.contains(" ")){
                action = command.substring(0, command.indexOf(" "));
                jsonData = command.substring(command.indexOf(" ") + 1);
            }
            else
                action = command;
            handleAction(action, jsonData);
        }
    }

}

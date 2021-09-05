package endpoints;

import APIs.RestaurantApi;
import APIs.UserApi;

import java.io.*;


public class CommandEndpoint {
    private InputStream inputStream;
    private RestaurantApi restaurantApi;
    private UserApi userProcess;


    public CommandEndpoint(InputStream inputStream){
        this.inputStream = inputStream;
        restaurantApi = RestaurantApi.getInstance();
        userProcess = new UserApi();
    }

    public String readCommand(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String response = "";
        try{
            String line = bufferedReader.readLine();
            while(line != null){
                String[] commandParts =  line.split(" ", 2);
                response += handleCommand(commandParts);
                response += "\n";
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return response.trim();
    }

    private String handleCommand(String[] commandParts){
        if(commandParts[0].equals("addRestaurant")){
            return restaurantApi.setRestaurantInfo(commandParts[1]);
        }
        else if(commandParts[0].equals("addFood")){
            return restaurantApi.addNewFood(commandParts[1]);
        }
        else if(commandParts[0].equals("getRestaurants")){
            return restaurantApi.getAllRestaurantNames();
        }
        else if(commandParts[0].equals("getRestaurant")){
            return restaurantApi.getRestaurantInfo(commandParts[1]);
        }
        else if(commandParts[0].equals("getFoods")){
            return restaurantApi.getFoodInfo(commandParts[1]);
        }
        else if(commandParts[0].equals("addToCart")){
            return userProcess.addToCart(commandParts[1]);
        }
        else if(commandParts[0].equals("getCart")){
            return userProcess.getCard();
        }
        else if(commandParts[0].equals("finalizeOrder")){
            return userProcess.finalizeOrder();
        }
        else if(commandParts[0].equals("getRecommendedRestaurants")){
            return userProcess.getMostThreePopularRestaurants();
        }
        else {
            return "False Command!";
        }
    }
}

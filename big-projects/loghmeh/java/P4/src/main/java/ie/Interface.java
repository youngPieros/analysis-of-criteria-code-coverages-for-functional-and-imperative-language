package ie;

import javax.print.DocFlavor;
import java.io.InputStream;
import java.util.Scanner;

public class Interface {
    static private Server server;
    enum Status {Success,Fail, Exit};

    static private String AddRestaurant = "addRestaurant";
    static private String AddFood = "addFood";
    static private String GetRestaurants = "getRestaurants";
    static private String GetRestaurant = "getRestaurant";
    static private String GetFood = "getFood";
    static private String AddToCart = "addToCart";
    static private String GetCart = "getCart";
    static private String FinalizeOrder = "finalizeOrder";
    static private String GetRecommendedRestaurants = "getRecommendedRestaurants";
    static private String Exit = "exit";


    public Interface(){
        server = new Server();
    }

    public String getInput(Scanner scanner){
        String input = scanner.nextLine();
        return input;
    }

    public String[] parse(String str, int maxNumOfTokens){
        String[] tokens = str.split(" ", maxNumOfTokens);
        for(int i = 0; i < tokens.length; i++)
            tokens[i] = tokens[i].trim();
        return tokens;
    }

    public Status runCommand(String[] commandTokens){
        if(commandTokens.length < 1)
            return Status.Fail;
        String commandType = commandTokens[0];
        if(commandType.equals(Exit))
            return Status.Exit;
        else if(commandType.equals(AddRestaurant) && commandTokens.length == 2)
            return server.addRestaurant(commandTokens[1]);
        else if(commandType.equals(AddFood) && commandTokens.length == 2)
            return server.addFood(commandTokens[1]);
        else if(commandType.equals(GetRestaurants) && commandTokens.length == 1)
            return server.printRestaurantsName();
        else if(commandType.equals(GetRestaurant) && commandTokens.length == 2)
            return server.getRestaurant(commandTokens[1]);
        else if(commandType.equals(GetFood) && commandTokens.length == 2)
            return server.getFood(commandTokens[1]);
        else if(commandType.equals(AddToCart) && commandTokens.length == 2)
            return server.addToCart(commandTokens[1]);
        else if(commandType.equals(GetCart) && commandTokens.length == 1)
            return server.getCart();
        else if(commandType.equals(FinalizeOrder) && commandTokens.length == 1)
            return server.finalizeOrder();
        else if(commandType.equals(GetRecommendedRestaurants) && commandTokens.length == 1)
            return server.getRecommendedRestaurants();
        else
            return Status.Fail;
    }

    public void run(InputStream inputStream){
        String input;
        String[] commandTokens;
        Status commandStatus;
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()){
            input = getInput(scanner);
            commandTokens = parse(input, 2);
            commandStatus = runCommand(commandTokens);
            if(commandStatus.equals(Status.Exit))
                break;
        }
        scanner.close();
    }
}

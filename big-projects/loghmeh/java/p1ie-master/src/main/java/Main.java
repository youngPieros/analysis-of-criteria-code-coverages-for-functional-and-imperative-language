import ir.ac.ut.ece.ie.OrderManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
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

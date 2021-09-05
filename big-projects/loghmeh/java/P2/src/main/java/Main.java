import ie.Runner;

import java.io.IOException;
import java.util.Scanner;
import org.json.*;
import java.util.ArrayList;
import java.util.Scanner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        Runner runner = new Runner();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String command = scanner.nextLine();
            runner.decideAct(command);

        }
    }

}

import endpoints.CommandEndpoint;

public class Main {
    public static void main(String[] args){
        CommandEndpoint commandEndpoint = new CommandEndpoint(System.in);
        System.out.println(commandEndpoint.readCommand());
    }
}

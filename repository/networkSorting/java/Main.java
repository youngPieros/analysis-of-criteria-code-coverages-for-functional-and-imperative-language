import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private ArrayList<Integer> numbers;
    private int networkSize;
    private ArrayList<String> network;
    private int numberOfStages;
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main networkHandler = new Main();
        networkHandler.start();
    }

    public void start() {
        while (true) {
            this.readNetworkParameters();
            if (this.isNetworkEmpty())
                break;
            this.readNetwork();
            if (isValidNetwork()) {
                for (int i = 0; i < this.numberOfStages; i++)
                    this.apply(i);
                if (this.isNetworkSorted()) {
                    System.out.println("Sorted");
                } else {
                    System.out.println("Not Sorted");
                }
            } else {
                System.out.println("Invalid network");
            }
        }
    }

    private boolean isNetworkEmpty() {
        return this.networkSize == 0 && this.numberOfStages == 0;
    }

    private void readNetworkParameters() {
        this.networkSize = scanner.nextInt();
        this.numberOfStages = scanner.nextInt();
    }

    private void readNetwork() {
        this.network = new ArrayList<>();
        this.numbers = new ArrayList<>();
        for (int i = 0; i < this.networkSize; i++)
            this.network.add(scanner.next());
        for (int i = 0; i < this.networkSize; i++)
            this.numbers.add(scanner.nextInt());
    }

    private boolean isValidNetwork() {
        for (int i = 0; i < this.numberOfStages; i++) {
            for (int j = 0; j < this.networkSize; j++) {
                int numberOfSameNet = 0;
                if (this.network.get(j).charAt(i) == '-')
                    continue;
                for (int k = 0; k < this.networkSize; k++)
                    if (this.network.get(j).charAt(i) == this.network.get(k).charAt(i))
                        numberOfSameNet++;
                if (numberOfSameNet != 2)
                    return false;
            }
        }
        return true;
    }

    private void apply(int stageNumber) {
        for (int i = 0; i < this.networkSize - 1; i++) {
            if (this.network.get(i).charAt(stageNumber) == '-')
                continue;
            for (int j = i + 1; j < this.networkSize; j++)
                if (this.network.get(i).charAt(stageNumber) == this.network.get(j).charAt(stageNumber) && this.numbers.get(i) > this.numbers.get(j))
                    Collections.swap(this.numbers, i, j);
        }
    }

    private boolean isNetworkSorted() {
        for (int i = 0; i < this.networkSize - 1; i++)
            if (this.numbers.get(i) > this.numbers.get(i + 1))
                return false;
        return true;
    }
}
package linearSearching.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main linearSearch = new Main();
        linearSearch.startProcess();
    }

    public void startProcess() {
        int collectionNumbers = scanner.nextInt();
        for (int i = 0; i < collectionNumbers; i++) {
            int collectionSize = scanner.nextInt();
            int searchedNumber = scanner.nextInt();
            ArrayList<Integer> collection = new ArrayList<>();
            for (int j = 0; j < collectionSize; j++)
                collection.add(scanner.nextInt());
            int index = findWithLinearSearchAlgorithm(searchedNumber, collection);
            System.out.println(index);
        }
    }

    public int findWithLinearSearchAlgorithm(int number, ArrayList<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++)
            if (numbers.get(i) == number)
                return i;
        return -1;
    }
}
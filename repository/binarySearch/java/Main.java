package binarySearch.java;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main collectionUtil = new Main();
        collectionUtil.startProcess();
    }

    public void startProcess() {
        int collectionNumbers = scanner.nextInt();
        for (int i = 0; i < collectionNumbers; i++) {
            int collectionSize = scanner.nextInt();
            int searchedNumber = scanner.nextInt();
            ArrayList <Integer> collection = new ArrayList<>();
            for (int j = 0; j < collectionSize; j++)
                collection.add(scanner.nextInt());
            int index = findWithBinarySearchAlgorithm(searchedNumber, collection);
            System.out.println(index);
        }
    }

    public int findWithBinarySearchAlgorithm(int number, ArrayList<Integer> collection) {
        int beginRange = 0;
        int endRange = collection.size();
        while (beginRange < endRange) {
            int middleRange = (beginRange + endRange) / 2;
            if (number == collection.get(middleRange))
                return middleRange;
            if (number < collection.get(middleRange))
                endRange = middleRange;
            else
                beginRange = middleRange + 1;
        }
        return -1;
    }
}
package selectionSort.java;

import java.util.Scanner;

public class Main {
    static int[] numbers;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            parseInputs();
            sort(numbers);
            System.out.println("sorted:\n" + ArrayToString(numbers));
        } catch (Exception e) {
            System.out.println("BAD VALUE");
        }
    }

    private static void parseInputs() {
        int numberSize = scanner.nextInt();
        numbers = new int[numberSize];
        for (int i = 0; i < numberSize; i++)
            numbers[i] = scanner.nextInt();
    }

    public static void swap(int[] numbers, int firstIndex, int secondIndex) {
    	int number = numbers[firstIndex];
    	numbers[firstIndex] = numbers[secondIndex];
    	numbers[secondIndex] = number;
    }

    public static void sort(int[] numbers) {
    	for (int i = 0; i < numbers.length - 1; i++) {
            int minIndex = i;
    		for (int j = i + 1; j < numbers.length; j++)
    			if (numbers[minIndex] > numbers[j])
    				minIndex = j;
            swap(numbers, i, minIndex);
        }
    }

    public static String ArrayToString(int[] numbers) {
        StringBuilder stringedArray = new StringBuilder("[");
        for (int i = 0; i < numbers.length - 1; i++)
            stringedArray.append(numbers[i]).append(",");
        if (numbers.length != 0)
            stringedArray.append(numbers[numbers.length - 1]);
        stringedArray.append("]");
        return stringedArray.toString();
    }
}
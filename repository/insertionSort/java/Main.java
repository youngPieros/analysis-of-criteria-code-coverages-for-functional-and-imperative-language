package insertionSort.java;

import java.util.Scanner;

public class Main {
    static int[] numbers;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        parseInputs();
        sort(numbers);
        System.out.println("sorted:\n" + ArrayToString(numbers));
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
    	for (int i = 1; i < numbers.length; i++)
            for (int j = i - 1; j > -1; j--)
                if (numbers[j] <= numbers[j+1])
                    break;
                else
                    swap(numbers, j, j+1);
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
package quickSort.java;

import java.util.Scanner;

public class Main {
    static int[] numbers;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            parseInputs();
            sort(numbers, 0, numbers.length - 1);
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

    public static int partition(int[] numbers, int beginIndex, int endIndex) {
        int pivotNumber = numbers[endIndex];
        int lastBiggestIndex = beginIndex - 1;
        for (int i = beginIndex; i < endIndex; i++) {
            if (numbers[i] < pivotNumber) {
                lastBiggestIndex++;
                swap(numbers, lastBiggestIndex, i);
            }
        }
        swap(numbers, ++lastBiggestIndex, endIndex);
        return lastBiggestIndex;
    }

    public static void sort(int[] numbers, int begin, int end) {
        if (end <= begin)
            return;
        int pivot = partition(numbers, begin, end);
        sort(numbers, begin, pivot - 1);
        sort(numbers, pivot + 1, end);
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
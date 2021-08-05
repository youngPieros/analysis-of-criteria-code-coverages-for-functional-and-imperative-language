import java.util.Arrays;
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

    public static void merge(int[] numbers, int beginIndex, int middleIndex, int endIndex) {
        int[] leftSorted = Arrays.copyOfRange(numbers, beginIndex, middleIndex + 1);
        int[] rightSorted = Arrays.copyOfRange(numbers, middleIndex + 1, endIndex + 1);
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = beginIndex;
        while (mergedIndex <= endIndex) {
            if (leftIndex == leftSorted.length)
                numbers[mergedIndex] = rightSorted[rightIndex++];
            else if (rightIndex == rightSorted.length)
                numbers[mergedIndex] = leftSorted[leftIndex++];
            else if (leftSorted[leftIndex] < rightSorted[rightIndex])
                numbers[mergedIndex] = leftSorted[leftIndex++];
            else
                numbers[mergedIndex] = rightSorted[rightIndex++];
            mergedIndex++;
        }
    }

    public static void sort(int[] numbers, int begin, int end) {
        if (end <= begin)
            return;
        int middleIndex = (begin + end) / 2;
        sort(numbers, begin, middleIndex);
        sort(numbers, middleIndex + 1, end);
        merge(numbers, begin, middleIndex, end);
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
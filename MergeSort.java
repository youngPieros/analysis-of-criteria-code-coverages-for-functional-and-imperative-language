import java.util.ArrayList;

public class MergeSort {
    static int[] numbers;

    public static void main(String[] args) {
        try {
            parseInputs(args);
            numbers = sort(numbers, 0, numbers.length - 1);
            System.out.println("sorted:\n" + ArrayToString(numbers));
        } catch (Exception e) {
            System.out.println("BAD VALUE");
        }
    }

    private static void parseInputs(String[] args) {
        numbers = new int[args.length];
        for (int i = 0; i < args.length; i++)
            numbers[i] = Integer.parseInt(args[i]);
    }

    public static int[] merge(int[] leftSorted, int[] rightSorted) {
        int[] mergedSort = new int[leftSorted.length + rightSorted.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;
        while (mergedIndex < leftSorted.length + rightSorted.length) {
            if (leftIndex == leftSorted.length)
                mergedSort[mergedIndex] = rightSorted[rightIndex++];
            else if (rightIndex == rightSorted.length)
                mergedSort[mergedIndex] = leftSorted[leftIndex++];
            else if (leftSorted[leftIndex] < rightSorted[rightIndex])
                mergedSort[mergedIndex] = leftSorted[leftIndex++];
            else
                mergedSort[mergedIndex] = rightSorted[rightIndex++];
            mergedIndex++;
        }
        return mergedSort;
    }

    public static int[] sort(int[] numbers, int begin, int end) {
        if (end <= begin)
            return new int[] {numbers[begin]};
        int middleIndex = (int) ((begin + end) / 2);
        int[] leftSorted = sort(numbers, begin, middleIndex);
        int[] rightSorted = sort(numbers, middleIndex + 1, end);
        return merge(leftSorted, rightSorted);
    }

    public static String ArrayToString(int[] numbers) {
        String stringified = "[";
        for (int i = 0; i < numbers.length - 1; i++)
            stringified += numbers[i] + ", ";
        stringified += numbers[numbers.length - 1] + "]";
        return stringified;
    }
}
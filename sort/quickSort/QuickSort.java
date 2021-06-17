package sort.quickSort;

public class QuickSort {
    static int[] numbers;

    public static void main(String[] args) {
        try {
            parseInputs(args);
            sort(numbers, 0, numbers.length - 1);
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
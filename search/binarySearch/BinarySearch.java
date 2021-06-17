package search.binarySearch;

public class BinarySearch {
    static int number;
    static int[] numbers;

    public static void main(String[] args) {
        try {
            parseInputs(args);
            int index = findWithBinarySearchAlgorithm(number, numbers);
            System.out.println("index: " + index);
        } catch (Exception e) {
            System.out.println("BAD VALUE");
        }
    }

    private static void parseInputs(String[] args) {
        number = Integer.parseInt(args[0]);
        numbers = new int[args.length - 1];
        for (int i = 1; i < args.length; i++)
            numbers[i - 1] = Integer.parseInt(args[i]);
    }

    public static int findWithBinarySearchAlgorithm(int number, int[] numbers) {
        int beginRange = 0;
        int endRange = numbers.length;
        while (beginRange < endRange) {
            int middleRange = (beginRange + endRange) / 2;
            if (number == numbers[middleRange])
                return middleRange;
            if (number < numbers[middleRange])
                endRange = middleRange;
            else
                beginRange = middleRange + 1;
        }
        return -1;
    }
}
package sort.bubbleSort;

public class BubbleSort {
    static int[] numbers;

    public static void main(String[] args) {
        try {
            parseInputs(args);
            sort(numbers);
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

    public static void sort(int[] numbers) {
    	for (int i = numbers.length - 1; i > 0; i--)
    		for (int j = 0; j < i; j++)
    			if (numbers[j] > numbers[j + 1])
    				swap(numbers, j, j + 1);
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
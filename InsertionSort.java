public class InsertionSort {
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
    	for (int i = 1; i < numbers.length; i++)
            for (int j = i - 1; j > -1; j--)
                if (numbers[j] <= numbers[j+1])
                    break;
                else
                    swap(numbers, j, j+1);
    }

    public static String ArrayToString(int[] numbers) {
    	String stringified = "[";
    	for (int i = 0; i < numbers.length - 1; i++)
    		stringified += numbers[i] + ", ";
    	stringified += numbers[numbers.length - 1] + "]";
    	return stringified;
    }
}
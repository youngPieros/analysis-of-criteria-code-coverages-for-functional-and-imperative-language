public class LinearSearch {
    static int number;
    static int[] numbers;

    public static void main(String[] args) {
        try {
            parseInputs(args);
            int index = findWithLinearSearchAlgorithm(number, numbers);
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

    public static int findWithLinearSearchAlgorithm(int number, int[] numbers) {
        for (int i = 0; i < numbers.length; i++)
            if (numbers[i] == number)
                return i;
        return -1;
    }
}
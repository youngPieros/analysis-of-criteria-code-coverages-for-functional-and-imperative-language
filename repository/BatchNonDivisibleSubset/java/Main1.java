package BatchNonDivisibleSubset.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main1 {
    private static final Scanner scanner = new Scanner(System.in);
    static int n;
    static int k;
    static ArrayList<Integer> numbers = new ArrayList<>();
    static ArrayList<Integer> buckets;

    public static void main(String[] args) {
        getInputs();
        int result = findMostNonDivisibleMembers();
        System.out.println(result);
    }

    public static void getInputs() {
        n = scanner.nextInt();
        k = scanner.nextInt();
        buckets = new ArrayList<>(Collections.nCopies(k, 0));
        for (int i = 0; i < n; i++)
            numbers.add(scanner.nextInt());
    }

    public static int findMostNonDivisibleMembers() {
        for (int number: numbers) {
            int index = number % k;
            buckets.set(index, buckets.get(index) + 1);
        }
        int result = Math.min(buckets.get(0), 1);
        for (int i = 1; i <= (k - 1) / 2; i++)
            result += Math.max(buckets.get(i), buckets.get(k - i));
        if (k % 2 == 0)
            result += Math.min(buckets.get((int)(k / 2)), 1);
        return result;
    }
}

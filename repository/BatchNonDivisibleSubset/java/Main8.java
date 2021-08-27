package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main8 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int k = scan.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scan.nextInt();
        }

        int[] counts = new int[k];
        for (int i = 0; i < a.length; i++) {
            counts[a[i] % k] += 1;
        }
        int count = Math.min(counts[0], 1);
        for (int i = 1; i < (k / 2) + 1; i++) {
            if (i != k - i) {
                count += Math.max(counts[i], counts[k-i]);
                if (k % 2 == 0) {
                    count += 1;
                }
            }
        }
        System.out.println(count);
    }
}
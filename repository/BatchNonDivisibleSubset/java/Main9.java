package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main9 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int a[] = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
        }
        System.out.println(findMaxSet(k, countMods(n, a, k)));
    }

    private static int findMaxSet(int k, int[] mods) {

        int total = 0;

        for (int i = 1; i < (k / 2) + 1; i++) {
            if (mods[i] > mods[k -i]) {
                total += mods[i];
            } else {
                total += mods[k - i];
            }
        }

        return total;
    }

    private static int[] countMods(int n, int[] a, int k) {
        int[] mods = new int[k];
        // initialize the counters.
        for (int i = 0; i < k; i++) {
            mods[i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int mod = a[i] % k;
            mods[mod] += 1;
        }
        return mods;
    }

}
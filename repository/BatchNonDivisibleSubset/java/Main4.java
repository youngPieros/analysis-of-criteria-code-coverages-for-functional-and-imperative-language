package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main4 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int key = in.nextInt() % k;
            if (map.get(key) == null) {
                map.put(key, 1);
            }
            else {
                int value = map.get(key);
                map.put(key, value + 1);
            }
        }
        int subArrayLength = 0;
        for (int key : map.keySet()) {
            if (key == 0) {
                continue;
            }
            if (k % 2 == 0 && key == k / 2) {
                continue;
            }
            if ((key > k / 2) && map.get(k - key) != null) {
                continue;
            }
            int a = map.get(key);
            if (map.get(k - key) != null) {
                int b = map.get(k - key);
                subArrayLength += Math.max(a, b);
            }
            else {
                subArrayLength += a;
            }
        }
        if (map.get(0) != null) {
            subArrayLength++;
        }
        if (k % 2 == 0 && map.get(k / 2) != null) {
            subArrayLength++;
        }
        System.out.println(subArrayLength);
    }
}
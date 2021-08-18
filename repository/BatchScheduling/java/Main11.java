import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main11 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] D = new int[n];
        int[] m = new int[n];
        long[] t = new long[n];
        for (int i = 0; i < n; i++) {
            D[i] = in.nextInt();
            m[i] = in.nextInt();
        }
        int index = 0;
        long maxOvershot = 0;
        for (int i = 1; i <= n; i++) {
            int insertIndex = sort(D, m, t, i);
            long time = 0;
            if (insertIndex < index) {
                time = t[index] + m[insertIndex] - m[index];
            }
            else {
                if (index == 0) time = 0;
                else time = t[index-1];
            }
            for (int j = index; j < i; j++) {
                time += m[j];
                t[j] = time;
                if (Math.max(0, time - D[j]) > maxOvershot) {
                    maxOvershot = Math.max(0, time - D[j]);
                    index = j;
                }
            }
            System.out.println(maxOvershot);
        }
    }

    public static int sort(int[] D, int[] m, long[] t, int len) {
        int currentD = D[len-1];
        int currentM = m[len-1];
        long currentT = t[len-1];
        int i = len-2;
        while (i >= 0 && D[i] > currentD) {
            D[i+1] = D[i];
            m[i+1] = m[i];
            t[i+1] = t[i];
            i--;
        }
        D[i+1] = currentD;
        m[i+1] = currentM;
        t[i+1] = currentT;
        return i + 1;
    }
}
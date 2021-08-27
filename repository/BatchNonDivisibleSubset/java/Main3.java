package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main3 {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] rems = new int[k];
        int a[] = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
            rems[a[a_i] % k] ++;
        }
        int sum = (rems[0] > 0) ? 1 : 0;
        for (int i=1; i <= (k / 2); i++){
            if (i *2 == k)
                sum += (rems[i] > 0) ? 1 : 0;
            else
                sum += Math.max(rems[i], rems[k  - i]);
        }
        System.out.println(sum);

    }
}
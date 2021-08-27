package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main10 {



    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        ArrayList<Integer> a = new ArrayList<Integer>();
        int[] fq = new int[k];

        for(int i = 0; i < n; ++i)
            fq[sc.nextInt() % k]++;
        //for(int i = 0; i < k; ++i)
        //System.out.print(fq[i]+ " ");

        int max = fq[0] == 0? 0 : 1;
        for(int i = 1; i < k; ++i) {

            max += Math.max(fq[i], fq[k-i]);
            fq[i] = fq[k-i] = 0;
        }



        System.out.println(max);

    }
}
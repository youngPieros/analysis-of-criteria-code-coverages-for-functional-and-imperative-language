package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main11 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        long a[] = new long[n];
        int b[] = new int[k];
        int z=0, x=0, y=0,v=3;
        for(int a_i=0; a_i < n; a_i++) { a[a_i]=in.nextLong();x=(int)(a[a_i]%((long)k));b[x]++;}
        v=0;for(int j=1;j<k/2+1;j++){v+=(b[j]>b[k-j])?b[j]:b[k-j];}
        //   System.out.println(v+" "+b[j]);}
        if(b[0]>0||(k%2==0&&b[k/2]>0))v++;
        System.out.println(v);
    }
}
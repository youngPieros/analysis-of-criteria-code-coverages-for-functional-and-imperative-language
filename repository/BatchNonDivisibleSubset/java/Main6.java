package BatchNonDivisibleSubset.java;

import java.util.Scanner;

public class Main6 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int k = s.nextInt();
        int[] arr = new int[n];
        int[] mod = new int[k];
        for(int i=0; i<n; i++){
            arr[i]=s.nextInt();
            mod[arr[i]%k]++;
        }
        int ans=0;
        if(mod[0]>0) ans++;
        if(k%2==0 && mod[k/2]>0) ans++;
        for(int i=1; i<k-i; i++){
            ans+=Math.max(mod[i],mod[k-i]);
        }

        System.out.println(ans);
    }

}
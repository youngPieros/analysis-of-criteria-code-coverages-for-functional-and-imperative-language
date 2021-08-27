package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main12 {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc= new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i<N;i++){
            arr[i]= sc.nextInt();
            arr[i]%=M;
        }
        Arrays.sort(arr);
        int count = 0;

        /*for (int i = 0; i<N;i++){
            System.out.println(arr[i]);
        }*/
        if((arr[N-1]== 0) || (N == 1)||(M%2 ==0 && arr[0]== M/2 &&arr[N-1]== M/2 )){
            System.out.println("0");
            return;
        }
        int [] a = new int[M];
        for (int i = 0; i<N;i++){
            a[arr[i]]++;
        }
        /*
        for (int i = 0; i<M;i++){
            System.out.println("a["+i+"]" +a[i]);
        }
        */
        if(a[0]>0){
            count++;
        }
        if(M%2 ==0 &&a[M/2]>0){
            count++;
        }
        for (int i = 1; i<(M+1)/2;i++){
            //System.out.println(i+" " +(M-i));
            count +=(a[i]>a[M-i])?a[i]:a[M-i];
        }
        System.out.println(+count);
    }
}
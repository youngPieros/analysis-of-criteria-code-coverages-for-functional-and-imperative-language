package BatchNonDivisibleSubset.java;

import java.util.*;

public class Main7 {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        for(int arr_i=0; arr_i < n; arr_i++){
            int integer = in.nextInt();
            list.add(new Integer(integer));
        }
        in.close();



        if(k == 1){
            System.out.println(k);
            return;
        }

        for(int i = 0;i < list.size();i ++){
            int x = list.get(i) % k;
            list.set(i, x);
        }

        int[] kx = new int[k];
        for(int i = 0;i < k;i ++){
            kx[i] = 0;
        }
        for(int i = 0;i < list.size();i ++){
            int x = list.get(i);
            kx[x] ++;
        }


        int num = 0;
        int A = 0;
        if(k % 2 == 0){
            A = k / 2 - 1;
        }else{
            A = k / 2;
        }
        if(kx[0] != 0){
            num += 2;
        }
        for(int i = 1; i <= A;i ++){
            if(kx[i] >= kx[k - i]){
                num += kx[i];
            }else {

                num += kx[k - i];
            }

        }
        System.out.println(num);
    }
}
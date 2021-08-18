import java.util.Scanner;

public class Main9 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int []a = new int[n];
        int []b = new int[n];
        int[]ans = new int[n];
        int max = 0, j,ansmax =0;
        int count = 0;
        for(int i = 0; i < n ; i++) {
            a[i] = s.nextInt();
            if(a[i] > max)
                max = a[i];
            int next = s.nextInt();
            count = count + next;
            b[i] = next;
            if(i > 0){
                for(j = 0; j < i; j++) {
                    if(a[i] == a[j])
                        break;

                }
                //	System.out.println("count ="+ count);
                //	System.out.println("max=" + max);
                if(j == i) {
                    ans[i] = count - max;
                    if(ans[i] > ansmax){
                        ansmax = ans[i];
                        System.out.println(ans[i]);
                    }
                    else
                        System.out.println(ansmax);
                }

                else
                {
                    // System.out.println(b[i]);
                    System.out.println((ansmax+b[i]));
                    ansmax = ansmax+b[i];

                }
            }
            else{
                if(b[i]>a[i])
                    System.out.println((b[i]- a[i]));
                else
                    System.out.println("0");
            }
        }
        s.close();
    }
}
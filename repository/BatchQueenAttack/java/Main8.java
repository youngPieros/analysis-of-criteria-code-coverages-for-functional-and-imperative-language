package BatchQueenAttack.java;

import java.util.*;

public class Main8 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();
        int maxPos = (n-1)*(n-1);

        for(int a0 = 0; a0 < k; a0++){
            int rObstacle = in.nextInt();
            int cObstacle = in.nextInt();

            if(rObstacle == rQueen){

                maxPos -= (n % cObstacle + 1);
            }
            else if(cObstacle == cQueen){

                maxPos -= (n % rObstacle + 1);
            }
        }
        System.out.println(maxPos);
    }
}
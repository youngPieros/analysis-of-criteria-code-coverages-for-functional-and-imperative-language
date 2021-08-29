package BatchQueenAttack.java;

import java.util.*;

public class Main11 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();
        int numSquaresHit = n - rQueen + (rQueen-1);
        numSquaresHit += n - cQueen + (cQueen-1);
        numSquaresHit += Math.min((n - rQueen), (n - cQueen)) + Math.min((rQueen-1), (cQueen-1));
        numSquaresHit += Math.min((n - rQueen), (cQueen -1) ) + Math.min((rQueen-1), (n - cQueen));
        //System.out.println("numSquaresHit " + numSquaresHit);
        for(int a0 = 0; a0 < k; a0++){
            //System.out.println("a0 "+a0);
            int rObstacle = in.nextInt();
            int cObstacle = in.nextInt();
            // your code goes here
            int rDiff = rQueen - rObstacle;
            int cDiff = cQueen - cObstacle;

            if(rDiff == cDiff){
                if(rDiff > 0){
                    numSquaresHit -= Math.min((n-rObstacle), (n-cObstacle));
                }
                else{
                    numSquaresHit -= Math.min((rObstacle-1), (cObstacle-1));
                }
            }
            else if(rDiff == 0 && cDiff !=0){
                if(cDiff > 0){
                    numSquaresHit -= (cObstacle);
                }
                else{
                    numSquaresHit -= (n - cObstacle);
                }
            }
            else if(cDiff == 0 && rDiff != 0){
                if(rDiff > 0){
                    numSquaresHit -= (rObstacle);
                }
                else{
                    numSquaresHit -= (n - rObstacle);
                }
            }
            //System.out.println("numSquaresHit " + numSquaresHit);
        }
        System.out.println(numSquaresHit);
    }
}
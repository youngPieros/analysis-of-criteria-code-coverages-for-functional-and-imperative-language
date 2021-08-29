package BatchQueenAttack.java;

import java.io.*;
import java.util.*;

public class Main12 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();
        Map<Integer, Integer> obstacles = new HashMap<>();

        int count = 0;

        for(int a0 = 0; a0 < k; a0++){
            int rObstacle = in.nextInt();
            int cObstacle = in.nextInt();
            obstacles.put(rObstacle,cObstacle);
        }
        System.out.println(noAttack(n, k, rQueen, cQueen, obstacles));
    }

    public static int noAttack(int n, int k, int rQueen, int cQueen,Map<Integer, Integer> obstacles) {
        int i = rQueen , j = cQueen;
        Map<Integer, Integer> obstacle = new HashMap<>();
        int numAttack = 2 * n-2 , numMin = 0;
        int count = 0;
        boolean f1 = false , f2 = false, f3 = false, f4 = false;

        while(i < n && j < n ) {
            if (!obstacle.containsKey(i)) {
                obstacle.put(i, j);
            }
            i++; j++;
            f1 = true;
        }

        i = rQueen; j = cQueen;
        while(i > 0 && j > 0 ) {
            if (!obstacle.containsKey(i)) {
                obstacle.put(i, j);
            }
            i--; j--;
            f2 = true;
        }

        i = rQueen; j = cQueen;
        while(i > 0 && j < n ) {
            if (!obstacle.containsKey(i)) {
                obstacle.put(i, j);
            }
            i--; j++;
            f3 = true;
        }

        i = rQueen; j = cQueen;
        while(i < n && j > 0 ) {
            if (!obstacle.containsKey(i)) {
                obstacle.put(i, j);
            }
            i++; j--;
            f4 = true;
        }

        for (Integer x: obstacles.keySet() ) {

            if (obstacle.containsKey(x)) {
                if (x > rQueen && obstacles.get(x)> cQueen) {
                    if (n-x > n - obstacles.get(x)) {
                        numMin += (n -obstacles.get(x));
                    }
                    else {
                        numMin += (n-x);
                    }
                }

                if (x < rQueen && obstacles.get(x)> cQueen) {
                    if (x > n - obstacles.get(x)) {
                        numMin += (n -obstacles.get(x));
                    }
                    else {
                        numMin += (x);
                    }
                }

                if (x > rQueen && obstacles.get(x) < cQueen) {
                    if (n-x > obstacles.get(x)) {
                        numMin += (obstacles.get(x));
                    }
                    else {
                        numMin += (n-x);
                    }
                }

                if (x < rQueen && obstacles.get(x) < cQueen) {
                    if (x > obstacles.get(x)) {
                        numMin += (n -obstacles.get(x));
                    }
                    else {
                        numMin += (x);
                    }
                }
            }
            else if(x.equals(rQueen)) {
                if (obstacles.get(x) > cQueen) {
                    numMin += n- obstacles.get(x);
                }
                numMin += obstacles.get(x);
            }
            else if(obstacles.get(x).equals(cQueen)) {
                if (x > rQueen) {
                    numMin += n- x;
                }
                numMin += x;
            }
        }
        if (f1)
            count ++;
        if (f2)
            count ++;
        if (f3)
            count ++;
        if (f4)
            count ++;

        return numAttack + obstacle.size()- numMin - count;
    }



}
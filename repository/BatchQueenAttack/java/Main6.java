package BatchQueenAttack.java;

import java.util.*;

public class Main6 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();
        int[] counter = new int[8];
        counter[0] = (n - rQueen < cQueen - 1) ? n - rQueen : cQueen - 1;
        counter[1] = n - rQueen;
        counter[2] = (n - rQueen < n - cQueen) ? n - rQueen : n - cQueen;
        counter[3] = cQueen - 1;
        counter[4] = n - cQueen;
        counter[5] = (rQueen - 1 < cQueen - 1) ? rQueen - 1 : cQueen - 1;
        counter[6] = rQueen - 1;
        counter[7] = (rQueen - 1 < n - cQueen) ? rQueen - 1 : n - cQueen;
        for(int a0 = 0; a0 < k; a0++){
            int rObstacle = in.nextInt();
            int cObstacle = in.nextInt();
            int rDiff = rQueen - rObstacle;
            int cDiff = cQueen - cObstacle;
            int diff = rDiff != 0 && cDiff != 0 ?
                    Math.min(Math.abs(rDiff), Math.abs(cDiff)) - 1 :
                    Math.max(Math.abs(rDiff), Math.abs(cDiff)) - 1;
            boolean isInline = Math.abs(rDiff) == Math.abs(cDiff);
            if (rDiff < 0) {
                if (cDiff > 0 && isInline && diff < counter[0])
                    counter[0] = diff;
                else if (cDiff == 0 && diff < counter[1])
                    counter[1] = diff;
                else if (cDiff < 0 && isInline && diff < counter[2])
                    counter[2] = diff;
            }
            else if (rDiff == 0) {
                if (cDiff > 0 && diff < counter[3])
                    counter[3] = diff;
                else if (cDiff < 0 && diff < counter[4])
                    counter[4] = diff;
            }
            else {
                if (cDiff > 0 && isInline && diff < counter[5])
                    counter[5] = diff;
                else if (cDiff == 0 && diff < counter[6])
                    counter[6] = diff;
                else if (cDiff < 0 && isInline && diff < counter[7])
                    counter[7] = diff;
            }
        }
        System.out.println(Arrays.stream(counter).sum());
    }
}
package BatchQueenAttack.java;

import java.util.*;

public class Main2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();

        int[] array = new int[8];
        array[0] = cQueen - 1;
        array[1] = n - cQueen;
        array[2] = rQueen - 1;
        array[3] = n - rQueen;
        array[4] = n - rQueen < cQueen - 1 ? n - rQueen : cQueen - 1;
        array[5] = n - rQueen < n - cQueen ? n - rQueen : n - cQueen;
        array[6] = cQueen - 1 < rQueen - 1 ? cQueen - 1 : rQueen - 1;
        array[7] = n - cQueen < rQueen - 1 ? n - cQueen : rQueen - 1;


        for(int a0 = 0; a0 < k; a0++){
            int row = in.nextInt();
            int column = in.nextInt();

            if (row == rQueen && column < cQueen) {    //left
                if (array[0] > cQueen - column - 1) {
                    array[0] = cQueen - column - 1;
                }
            } else if (row == rQueen && column > cQueen) {  //right
                if (array[1] > column - cQueen - 1) {
                    array[1] = column - cQueen - 1;
                }
            } else if (column == cQueen && row < rQueen) { //down
                if (array[2] > rQueen - row - 1) {
                    array[2] = rQueen - row - 1;
                }
            } else if (column == cQueen && row > rQueen) {  //up
                if (array[3] > row - rQueen - 1) {
                    array[3] = row - rQueen - 1;
                }
            } else if (row > rQueen && column < cQueen && row - rQueen == cQueen - column) {  //left up
                if (array[4] > row - rQueen - 1) {
                    array[4] = row - rQueen - 1;
                }
            } else if (row > rQueen && column > cQueen && row - rQueen == column - cQueen) {  //right up
                if (array[5] > row - rQueen - 1) {
                    array[5] = row - rQueen - 1;
                }
            } else if (column < cQueen && row < rQueen && cQueen - column == rQueen - row) {  //left down
                if (array[6] > cQueen - column - 1) {
                    array[6] = cQueen - column - 1;
                }
            } else if (column > cQueen && row < rQueen && column - cQueen == rQueen - row) { // right down
                if (array[7] > column - cQueen - 1) {
                    array[7] = column - cQueen - 1;
                }
            }
        }

        long sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += array[i];
        }

        System.out.println(sum);

    }
}
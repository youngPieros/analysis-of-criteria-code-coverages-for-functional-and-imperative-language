package BatchQueenAttack.java;

import java.util.*;

public class Main5 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int xq = in.nextInt();
        int yq = in.nextInt();
        HashMap<Integer, Integer>[] map = new HashMap[n + 1];
        int count = 0;
        int total = 0;
        int[] c = new int[9];
        total += 2 * (n - 1);
        total += Math.min(yq - 1, n - xq);
        total += Math.min(n - xq, n - yq);
        total += Math.min(yq - 1, xq - 1);
        total += Math.min(n - yq, xq - 1);

        for (int i = 0; i <= n; i++) {
            map[i] = new HashMap<>();
        }

        for (int a0 = 0; a0 < k; a0++) {
            int x = in.nextInt();
            int y = in.nextInt();

            if (!map[x].containsKey(y)) {

                map[x].put(y, 0);

                if (x == xq) {
                    if (y > yq) {
                        if (c[1] < (n - y + 1)) {
                            count += (n - y + 1) - c[1];
                            c[1] = n - y + 1;
                        }

                    } else {
                        if (c[2] < y) {
                            count += y - c[2];
                            c[2] = y;
                        }

                    }
                } else if (y == yq) {
                    if (x > xq) {
                        if (c[3] < (n - x + 1)) {
                            count += (n - x + 1) - c[3];
                            c[3] = n - x + 1;
                        }

                    } else {
                        if (c[4] < x) {
                            count += x - c[4];
                            c[4] = x;
                        }

                    }
                } else if (Math.abs(x - xq) == Math.abs(y - yq)) {
                    if (x > xq && y < yq) {
                        if (c[5] < Math.min(y, n - x + 1)) {
                            //System.out.println("hii"+"  "+Math.min(y, n - x + 1)+" "+count);
                            count += Math.min(y, n - x + 1) - c[5];

                            c[5] = Math.min(y, n - x + 1);
                        }

                    } else if (x > xq && y > yq) {
                        if (c[6] < Math.min(n - x + 1, n - y + 1)) {
                            count += Math.min(n - x + 1, n - y + 1) - c[6];
                            c[6] = Math.min(n - x + 1, n - y + 1);
                        }

                    } else if (x < xq && y < yq) {
                        if (c[7] < Math.min(y, x)) {
                            count += Math.min(y, x) - c[7];
                            c[7] = Math.min(y, x);
                        }

                    } else if (x < xq && y > yq) {
                        if (c[8] < Math.min(n - y + 1, x)) {
                            count += Math.min(n - y + 1, x) - c[8];
                            c[8] = Math.min(n - y + 1, x);
                        }

                    }
                }
            }
        }

        System.out.println(total - count);
    }



}
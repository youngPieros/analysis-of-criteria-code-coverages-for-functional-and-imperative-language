package BatchQueenAttack.java;

import java.util.*;

public class Main3 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rQueen = in.nextInt();
        int cQueen = in.nextInt();
        int [][] deltas = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
        HashMap<Integer,HashSet<Integer>> obstacles = new HashMap<>();
        for(int a0 = 0; a0 < k; a0++){
            int rObstacle = in.nextInt();
            int cObstacle = in.nextInt();
            if (!obstacles.containsKey(rObstacle)) obstacles.put(rObstacle,new HashSet<>());
            obstacles.get(rObstacle).add(cObstacle);
        }

        int count = 0;
        for (int d = 0; d<8; d++) {
            int [] delta = deltas[d];
            int r = rQueen;
            int c = cQueen;
            while (true) {
                r += delta[0];
                c += delta[1];
                if (r<=0 || c<=0 || r>n || c>n || (obstacles.getOrDefault(r,new HashSet<>()).contains(c))) {
                    break;
                }
                count++;
            }
        }
        System.out.println(count);
    }
}
import java.util.HashSet;
import java.util.Scanner;

public class Main10 {
    static int removed = 0;
    static boolean[] dead;

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int m = scn.nextInt();
        int[] lower = new int[n + 1];
        HashSet<Integer>[] adjList = new HashSet[n + 1];
        dead = new boolean[n + 1];


        for (int i = 1; i <= n; i++) {
            adjList[i] = new HashSet<>();
        }
        for (int i = 0; i < m; i++) {
            int u = scn.nextInt();
            int v = scn.nextInt();
            int min = Math.min(u, v);
            int max = Math.max(u, v);
            lower[max]++;
            adjList[min].add(max);
        }

        for (int i = 1; i <= n; i++) {

            if (lower[i] == 0 && adjList[i].size() > 0)
                remove(i, adjList, lower);
        }
        int q = scn.nextInt();
        while (q-- > 0) {
            int type = scn.nextInt();
            if (type == 3) {
                System.out.println(n - removed);
                continue;
            }
            int u = scn.nextInt();
            int v = scn.nextInt();
            int max = Math.max(u, v);
            int min = Math.min(u, v);
            if (type == 2) {
                adjList[min].remove(max);
                lower[max]--;
                if (lower[max] == 0&&adjList[max].size()>0  ) remove(max, adjList, lower);
            }
            if (type == 1) {
                lower[max]++;
                adjList[min].add(max);
                if (lower[min] == 0) remove(min, adjList, lower);
            }
        }
    }

    static void remove(int u, HashSet<Integer>[] adjList, int[] lower) {
        if (dead[u]) return;
        for (int neighbor : adjList[u]) {
            if (--lower[neighbor] == 0 && adjList[neighbor].size() > 0)
                remove(neighbor, adjList, lower);
        }
        dead[u] = true;
        removed++;
    }


}

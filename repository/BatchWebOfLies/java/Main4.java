package BatchWebOfLies.java;

import java.io.*;
import java.util.*;
public class Main4 {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() throws java.io.IOException {
            br = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    public static void main(String[] args) throws IOException {
        FastReader input = new FastReader();
        int nobles = input.nextInt();
        int starting = input.nextInt();
        TreeSet<Integer>[] list = new TreeSet[nobles];
        for (int i = 0; i < list.length; i++) {
            list[i] = new TreeSet<Integer>();
        }
        boolean[] winning = new boolean[nobles];
        int counter = 0;
        for (int i = 0; i < starting; i++) {
            int n1 = input.nextInt() - 1;
            int n2 = input.nextInt() - 1;
            list[n1].add(n2);
            list[n2].add(n1);
        }
        for (int i = 0; i < nobles; i++) {
            list[i].add(i);
        }
        for (int i = 0; i < nobles; i++) {
            if (i == list[i].last()) {
                counter++;
            }
        }

        int queries = input.nextInt();
        for (int i = 0; i < queries; i++) {
            int type = input.nextInt();
            if (type == 1) {
                int n1 = input.nextInt() - 1;
                int n2 = input.nextInt() - 1;
                if (n2 > n1 && n1 == list[n1].last()) {
                    counter--;
                }
                if (n1 > n2 && n2 == list[n2].last()) {
                    counter--;
                }
                list[n1].add(n2);
                list[n2].add(n1);
            } else if (type == 2) {
                int n1 = input.nextInt() - 1;
                int n2 = input.nextInt() - 1;
                list[n1].remove(n2);
                list[n2].remove(n1);
                if (n2 > n1 && n1 == list[n1].last()) {
                    counter++;
                }
                if (n1 > n2 && n2 == list[n2].last()) {
                    counter++;
                }
            } else if (type == 3) {
                System.out.println(counter);
            }
        }
    }
}

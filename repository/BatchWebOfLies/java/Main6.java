package BatchWebOfLies.java;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main6 {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        APautinaLzhi solver = new APautinaLzhi();
        solver.solve(1, in, out);
        out.close();
    }

    static class APautinaLzhi {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] a = new int[n + 1];

            int ans = n;
            for (int i = 0; i < m; i++) {
                int from = in.nextInt();
                int to = in.nextInt();
                if (from < to) {
                    if (a[from] == 0) {
                        ans--;
                    }
                    a[from]++;
                } else {
                    if (a[from] == 0) {
                        ans--;
                    }
                    a[to]++;
                }
            }
            int qq = in.nextInt();
            for (int i = 0; i < qq; i++) {
                int type = in.nextInt();
                if (type == 3) {
                    out.println(ans);
                    continue;
                }
                int from = in.nextInt();
                int to = in.nextInt();
                if (from > to) {
                    int z = from;
                    from = to;
                    to = z;
                }
                if (type == 1) {
                    if (a[from] == 0) {
                        ans--;
                    }
                    a[from]++;
                } else {
                    if (a[from] == 1) {
                        ans++;
                    }
                    a[from]--;
                }
            }
        }

    }

    static class InputReader {
        private static final int BUFFER_LENGTH = 1 << 10;
        private InputStream stream;
        private byte[] buf = new byte[BUFFER_LENGTH];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int nextC() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = skipWhileSpace();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = nextC();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = nextC();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public int skipWhileSpace() {
            int c = nextC();
            while (isSpaceChar(c)) {
                c = nextC();
            }
            return c;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

    }
}


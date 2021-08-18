import java.util.*;

public class Main4 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int T = scan.nextInt();
        int[] ds = new int[T], ts = new int[T];
        int size = 0;

        for (int i = 0; i < T; i++) {
            int d = scan.nextInt(), m = scan.nextInt();

            int pos = Arrays.binarySearch(ds, 0, size, d);
            if (pos < 0)
                pos = -pos - 1;

            for (int o = pos; o < size; o++)
                ts[o] += m;
            m += (pos > 0 ? ds[pos-1] + ts[pos-1] : 0) - d;

            if (size == pos || m > ts[pos]) {
                System.arraycopy(ds, pos, ds, pos+1, size - pos);
                System.arraycopy(ts, pos, ts, pos+1, size - pos);
                ds[pos] = d;
                ts[pos] = m;
                size++;
            }

            int jump = 0;
            for (jump = 0; jump < pos && ts[pos-jump-1] <= ts[pos]; jump++);
            if (jump > 0) {
                System.arraycopy(ds, pos, ds, pos-jump, size - pos);
                System.arraycopy(ts, pos, ts, pos-jump, size - pos);
                size -= jump;
            }

            System.out.println(Math.max(ts[0],0));
        }
    }
}
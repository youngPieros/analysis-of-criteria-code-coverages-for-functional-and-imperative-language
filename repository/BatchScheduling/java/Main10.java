import java.util.Scanner;

public class Main10 {
    /** Given a list of tasks, schedule them so them a task overshoots the deadline by the least amount of time.  */

    private int taskCount;
    private int entries = 0;

    private long[] overShoot = new long[100001];
    private long[] timeToFinish = new long[100000];
    private int[] tasks = new int[100000];

    public int findRank(int lo, int hi, int val) {
        if (hi < lo)
            return lo;

        int mid = lo + (hi - lo) / 2;
        if (tasks[mid] == val)
            return mid;
        if (tasks[mid] < val)
            return findRank(mid + 1, hi, val);
        return findRank(lo, mid - 1, val);
    }

    public void insertItem(int val, int m) {
        int index = findRank(0, entries - 1, val);

        for (int i = entries; i > index; i--) {
            tasks[i] = tasks[i - 1];
            timeToFinish[i] = timeToFinish[i - 1] + m;

            overShoot[i] = Math.max(overShoot[i + 1], timeToFinish[i] - tasks[i]);
        }

        tasks[index] = val;
        if (index > 0) timeToFinish[index] = timeToFinish[index - 1] + m;
        else timeToFinish[index] = m;
        overShoot[index] = Math.max(overShoot[index + 1], timeToFinish[index] - tasks[index]);
        overShoot[0] = Math.max(overShoot[0], overShoot[index]);

        //for (int i = index; i >= 0; i--)
        //    overShoot[i] = Math.max(overShoot[i + 1], timeToFinish[i] - tasks[i].d);
        entries++;
    }

    void run() {
        try (Scanner in = new Scanner(System.in);) {
            taskCount = in.nextInt();

            for (int i = 0; i < taskCount; i++) {
                insertItem(in.nextInt(), in.nextInt());
                System.out.println(overShoot[0]);
            }
        }
    }

    public static void main(String[] args) {
        Main10 ts = new Main10();
        ts.run();
    }
}
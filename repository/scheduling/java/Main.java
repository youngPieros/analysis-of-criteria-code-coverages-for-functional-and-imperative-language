import java.util.*;

class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LinkedList<Integer> deadlines = new LinkedList<>();
    private static final LinkedList<Integer> minutes = new LinkedList<>();

    public static void addTaskScheduling(int deadline, int minute) {
        ListIterator<Integer> deadlineIterator = deadlines.listIterator();
        ListIterator<Integer> minutesIterator = minutes.listIterator();
        while (deadlineIterator.hasNext()) {
            int currentMinute = minutesIterator.next();
            int currentDeadline = deadlineIterator.next();
            if (deadline < currentDeadline || deadline == currentDeadline && minute <= currentMinute) {
                deadlineIterator.previous();
                minutesIterator.previous();
                deadlineIterator.add(deadline);
                minutesIterator.add(minute);
                return;
            }
        }
        deadlineIterator.add(deadline);
        minutesIterator.add(minute);
    }

    public static int findMinCostInBestScheduling() {
        ListIterator<Integer> deadlineIterator = deadlines.listIterator();
        ListIterator<Integer> minutesIterator = minutes.listIterator();
        int cost = 0;
        int deadline = 0;
        while (deadlineIterator.hasNext()) {
            int taskDeadline = deadlineIterator.next();
            deadline += minutesIterator.next();
            if (deadline - taskDeadline > cost)
                cost = deadline - taskDeadline;
        }
        return cost;
    }

    public static void main(String[] args) {
        int numberOfTasks = scanner.nextInt();

        for (int t = 0; t < numberOfTasks; t++) {
            int deadline = scanner.nextInt();
            int minute = scanner.nextInt();
            addTaskScheduling(deadline, minute);
            int cost = findMinCostInBestScheduling();
            System.out.println(cost);
        }
    }
}

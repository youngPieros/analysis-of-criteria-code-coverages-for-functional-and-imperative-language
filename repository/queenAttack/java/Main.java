package queenAttack.java;

import java.util.*;

public class Main {
    enum Direction {
        UP, RIGHT, DOWN, LEFT,
        RIGHT_UP, RIGHT_DOWN, LEFT_DOWN, LEFT_UP,
    }

    class Point {
        int raw;
        int column;

        public Point(int raw, int column) {
            this.raw = raw;
            this.column = column;
        }
        public Direction getDirectionTo(Point point) {
            if (this.raw == point.raw)
                return this.column > point.column ? Direction.LEFT : Direction.RIGHT;
            if (this.column == point.column)
                return this.raw > point.raw ? Direction.DOWN : Direction.UP;
            if (Math.abs(this.raw - point.raw) != Math.abs(this.column - point.column))
                return null;
            if (this.raw > point.raw && this.column > point.column) return Direction.LEFT_DOWN;
            if (this.raw > point.raw) return Direction.RIGHT_DOWN;
            if (this.column > point.column) return Direction.LEFT_UP;
            return Direction.RIGHT_UP;
        }
        public int getDistance(Point point) {
            if (this.raw == point.raw)
                return Math.abs(this.column - point.column);
            return Math.abs(this.raw - point.raw);
        }
    }

    static int n;
    static int k;
    static Point queenPosition;
    static ArrayList<Point> obstacles = new ArrayList<>();
    static HashMap<Direction, Integer> pointsUnderAttack = new HashMap<>();


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main solver = new Main();
        solver.readInputs();
        solver.initialUnderAttackPoints();
        int result = solver.solve();
        System.out.println(result);
    }

    public void readInputs() {
        n = scanner.nextInt();
        k = scanner.nextInt();
        queenPosition = new Point(scanner.nextInt(), scanner.nextInt());
        for (int i = 0; i < k; i++)
            obstacles.add(new Point(scanner.nextInt(), scanner.nextInt()));
    }

    public void initialUnderAttackPoints() {
        pointsUnderAttack.put(Direction.UP, n - queenPosition.raw);
        pointsUnderAttack.put(Direction.DOWN, queenPosition.raw - 1);
        pointsUnderAttack.put(Direction.LEFT, queenPosition.column - 1);
        pointsUnderAttack.put(Direction.RIGHT, n - queenPosition.column);
        pointsUnderAttack.put(Direction.LEFT_UP, Math.min(queenPosition.column - 1, n - queenPosition.raw));
        pointsUnderAttack.put(Direction.LEFT_DOWN, Math.min(queenPosition.column - 1, queenPosition.raw - 1));
        pointsUnderAttack.put(Direction.RIGHT_UP, Math.min(n - queenPosition.column, n - queenPosition.raw));
        pointsUnderAttack.put(Direction.RIGHT_DOWN, Math.min(n - queenPosition.column, queenPosition.raw - 1));
    }

    public int solve() {
        int result = 0;
        for (int i = 0; i < k; i ++) {
            Point obstacle = obstacles.get(i);
            Direction direction = queenPosition.getDirectionTo(obstacle);
            if (direction != null && pointsUnderAttack.get(direction) > queenPosition.getDistance(obstacle) - 1)
                pointsUnderAttack.put(direction, queenPosition.getDistance(obstacle) - 1);
        }
        for (Map.Entry<Direction, Integer> direction: pointsUnderAttack.entrySet()) {
            result += direction.getValue();
        }
        return result;
    }
}
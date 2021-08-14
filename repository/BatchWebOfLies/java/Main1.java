import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main1 {
    private static final int ADD_NOBLE_COMMAND = 1;
    private static final int REMOVE_NOBLE_COMMAND = 2;
    private static final int KILL_NOBLE_COMMAND = 3;

    int numberOfHumans;
    private ArrayList<Noble> nobles = new ArrayList<>();
    private ArrayList<Noble> backupNobles = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);


    private static class Noble {
        private final int power;
        private final int id;
        private boolean isAlive = true;
        private Set<Noble> friends = new HashSet<>();
        public Noble(int id, int power) {
            this.id = id;
            this.power = power;
        }
        public void removeFriend(Noble friend) {
            this.friends.remove(friend);
        }
        public void addFriend(Noble friend) {
            this.friends.add(friend);
        }
        public boolean isVulnerable() {
            if (this.friends.size() == 0 || !this.isAlive)
                return false;
            for (Noble friend : this.friends)
                if (friend.power < this.power)
                    return false;
            return true;
        }
        public void kill() {
            this.isAlive = false;
            for (Noble friend: this.friends)
                friend.removeFriend(this);
            this.friends.clear();
        }

        public Noble clone() {
            return new Noble(this.id, this.power);
        }
    }

    public static void main(String[] args) {
        startWebLies();
    }

    public static void startWebLies() {
        Main1 webOfLiesApplication = new Main1();
        webOfLiesApplication.scanNetRelations();
        int queryNumber = scanner.nextInt();
        for (int j = 0; j < queryNumber; j++)
            webOfLiesApplication.runCommand();
    }

    public void scanNetRelations() {
        this.numberOfHumans = scanner.nextInt();
        int numberOfRelations = scanner.nextInt();
        for (int i = 0; i < this.numberOfHumans; i++)
            this.nobles.add(new Noble(i, i));
        for(int i = 0; i < numberOfRelations; i++) {
            int firstNobleNumber = scanner.nextInt();
            int secondNobleNumber = scanner.nextInt();
            this.addNobleRelation(firstNobleNumber - 1, secondNobleNumber - 1);
        }
        this.backupNobles = new ArrayList<>(nobles);
    }

    public void runCommand() {
        int commandType = scanner.nextInt();
        switch (commandType) {
            case ADD_NOBLE_COMMAND:
                int firstNobleNumber = scanner.nextInt();
                int secondNobleNumber = scanner.nextInt();
                addNobleRelation(firstNobleNumber - 1, secondNobleNumber - 1);
                break;
            case REMOVE_NOBLE_COMMAND:
                firstNobleNumber = scanner.nextInt();
                secondNobleNumber = scanner.nextInt();
                this.removeNobleRelation(firstNobleNumber - 1, secondNobleNumber - 1);
                break;
            case KILL_NOBLE_COMMAND:
                ArrayList<Noble> backup = getBackupNobles(this.nobles);
                this.killAllVulnerableNobles();
                System.out.println(getNumberOfSurvivingHumans());
                this.nobles = backup;
                break;
        }
    }

    public void addNobleRelation(int firstNobleNumber, int secondNobleNumber) {
        Noble firstNoble = this.nobles.get(firstNobleNumber);
        Noble secondNoble = this.nobles.get(secondNobleNumber);
        firstNoble.addFriend(secondNoble);
        secondNoble.addFriend(firstNoble);
    }

    public void removeNobleRelation(int firstNobleNumber, int secondNobleNumber) {
        Noble firstNoble = this.nobles.get(firstNobleNumber);
        Noble secondNoble = this.nobles.get(secondNobleNumber);
        firstNoble.removeFriend(secondNoble);
        secondNoble.removeFriend(firstNoble);
    }

    public void killAllVulnerableNobles() {
        ArrayList<Noble> vulnerableNobles = new ArrayList<>();
        for (Noble noble: this.nobles)
            if (noble.isVulnerable())
                vulnerableNobles.add(noble);
        for (Noble vulnerableNoble: vulnerableNobles)
            vulnerableNoble.kill();
        if (vulnerableNobles.size() > 0)
            this.killAllVulnerableNobles();
    }

    public int getNumberOfSurvivingHumans() {
        int counter = 0;
        for (Noble noble: this.nobles)
            if (noble.isAlive)
                counter++;
        return counter;
    }

    public ArrayList<Noble> getBackupNobles(ArrayList<Noble> nobles) {
        ArrayList<Noble> backup = new ArrayList<>();
        for (Noble noble: nobles)
            backup.add(noble.clone());
        for (int i = 0; i < nobles.size(); i++)
            for (Noble friend: nobles.get(i).friends)
                backup.get(i).addFriend(backup.get(friend.id));
        return backup;
    }
}

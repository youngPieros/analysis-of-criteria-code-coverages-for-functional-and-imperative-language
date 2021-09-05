package ir.ut.exception;

public class StudentNotFoundException extends BolbolestanException {
    /**
     *
     */
    private static final long serialVersionUID = 5049263369115389775L;

    public StudentNotFoundException() {
        super("No student found with the given code");
    }
}

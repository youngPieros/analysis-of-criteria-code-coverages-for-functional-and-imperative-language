package ir.ut.exception;

public class DuplicateStudentException extends BolbolestanException {

    /**
     *
     */
    private static final long serialVersionUID = 588035026244437437L;

    public DuplicateStudentException() {
        super("There is another student with this studentId.");
    }

}

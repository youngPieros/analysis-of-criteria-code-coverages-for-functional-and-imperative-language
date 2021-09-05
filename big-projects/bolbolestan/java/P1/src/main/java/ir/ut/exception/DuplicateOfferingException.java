package ir.ut.exception;

public class DuplicateOfferingException extends BolbolestanException {

    /**
     *
     */
    private static final long serialVersionUID = 588035026244437437L;

    public DuplicateOfferingException() {
        super("There is another offering with this code.");
    }

}

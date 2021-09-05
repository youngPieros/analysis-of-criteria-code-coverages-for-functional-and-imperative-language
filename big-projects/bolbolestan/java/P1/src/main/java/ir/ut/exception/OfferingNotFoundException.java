package ir.ut.exception;

public class OfferingNotFoundException extends BolbolestanException {
    /**
     *
     */
    private static final long serialVersionUID = 938524952791389648L;

    public OfferingNotFoundException() {
        super("No offering found with the given code.");
    }
}

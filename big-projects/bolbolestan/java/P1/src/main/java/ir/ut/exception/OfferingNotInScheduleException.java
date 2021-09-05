package ir.ut.exception;

public class OfferingNotInScheduleException extends BolbolestanException {
    /**
     *
     */
    private static final long serialVersionUID = 8422320210164585667L;

    public OfferingNotInScheduleException() {
        super("The schedule doesn't contain this offering.");
    }
}

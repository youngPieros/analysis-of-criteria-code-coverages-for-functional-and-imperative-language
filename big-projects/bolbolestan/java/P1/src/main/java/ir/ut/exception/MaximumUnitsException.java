package ir.ut.exception;

public class MaximumUnitsException extends BolbolestanException {

    private static final long serialVersionUID = 6617892433069505650L;

    public MaximumUnitsException() {
        super("You can have at most 20 units in your schedule.");
    }

}

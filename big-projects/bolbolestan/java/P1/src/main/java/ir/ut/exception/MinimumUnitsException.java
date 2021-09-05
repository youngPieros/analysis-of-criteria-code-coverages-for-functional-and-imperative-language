package ir.ut.exception;

public class MinimumUnitsException extends BolbolestanException {

    private static final long serialVersionUID = 6617892433069505650L;

    public MinimumUnitsException() {
        super("You must have at least 12 units to finalize your schedule.");
    }

}

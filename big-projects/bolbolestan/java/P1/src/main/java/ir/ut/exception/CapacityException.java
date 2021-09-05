package ir.ut.exception;

public class CapacityException extends BolbolestanException {

    private static final long serialVersionUID = 6617892433069505650L;

    public CapacityException(String code) {
        super(String.format("The offering is full (%s).", code));
    }

}

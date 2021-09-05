package ir.ut.exception;

public class ClassTimeCollisionException extends BolbolestanException {

    private static final long serialVersionUID = 6617892433069505650L;

    public ClassTimeCollisionException(String code1, String code2) {
        super(String.format("class time collision %s %s.", code1, code2));
    }

}

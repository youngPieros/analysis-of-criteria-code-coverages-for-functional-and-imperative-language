package ir.ut.exception;

public class ExamTimeCollisionException extends BolbolestanException {

    private static final long serialVersionUID = 6617892433069505650L;

    public ExamTimeCollisionException(String code1, String code2) {
        super(String.format("exam time collision %s %s.", code1, code2));
    }

}

package uoc.ds.pr.exceptions;


public class AttendeeNotInEventException extends DSException {
    private static final long serialVersionUID = -2577150645305791318L;

    public AttendeeNotInEventException() {
    }

    public AttendeeNotInEventException(String message) {
        super(message);
    }
}

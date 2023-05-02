package uoc.ds.pr.exceptions;

public class AttendeeAlreadyInEventException extends DSException {

    private static final long serialVersionUID = -2577150645305791318L;

    public AttendeeAlreadyInEventException() {    }

    public AttendeeAlreadyInEventException(String message) {
        super(message);
    }
}

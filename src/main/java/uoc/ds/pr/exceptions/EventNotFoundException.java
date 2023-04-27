package uoc.ds.pr.exceptions;


public class EventNotFoundException extends DSException {
    private static final long serialVersionUID = -2577150645305791318L;

    public EventNotFoundException() {
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}

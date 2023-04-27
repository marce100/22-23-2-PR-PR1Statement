package uoc.ds.pr.exceptions;


public class NotAllowedException extends DSException {
    private static final long serialVersionUID = -2577150645305791318L;

    public NotAllowedException() {
    }

    public NotAllowedException(String message) {
        super(message);
    }
}

package uoc.ds.pr.exceptions;


public class EntityNotFoundException extends DSException {
    private static final long serialVersionUID = -2577150645305791318L;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}

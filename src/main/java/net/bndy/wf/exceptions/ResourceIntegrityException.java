package net.bndy.wf.exceptions;

public class ResourceIntegrityException extends AppException {

    public ResourceIntegrityException(String message) {
        super(message);
    }

    public ResourceIntegrityException(String message, Throwable t) {
        super(message, t);
    }
}

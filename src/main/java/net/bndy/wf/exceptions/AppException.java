package net.bndy.wf.exceptions;

import net.bndy.wf.ApplicationContext;

public class AppException extends Exception {
	private static final long serialVersionUID = 1L;
	private Throwable innerException;

    public AppException() {

    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable t) {
        this(message);
        this.innerException = t;
    }

    @Override
    public String getMessage() {
        String message =  super.getMessage();
        if (message != null && !message.isEmpty()) {
             String result = ApplicationContext.language(message);
             if (result != null && !result.isEmpty()) {
                 return result;
             }
        }
        return message;
    }

    public Throwable getInnerException() {
        return innerException;
    }
}

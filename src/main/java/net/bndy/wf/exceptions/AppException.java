package net.bndy.wf.exceptions;

import net.bndy.wf.ApplicationContext;

public class AppException extends Exception {

    private String title;

    public String getTitle() {
        if (title != null && !title.isEmpty()) {
            return ApplicationContext.language(title);
        }

        return null;
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String title, String message) {
        super(message);
        this.title = title;
    }

    @Override
    public String getMessage() {
        String message =  super.getMessage();
        if (message != null && !message.isEmpty()) {
            return ApplicationContext.language(message);
        }
        return message;
    }
}

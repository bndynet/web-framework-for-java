package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceIntegrityException extends AppException {

    public ResourceIntegrityException(String message) {
        super(message);
    }

    public ResourceIntegrityException(String message, Throwable t) {
        super(message, t);
    }
}

package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AppException {

    public UnauthorizedException() {
        super("error.unauthorizedAccess");
    }
}

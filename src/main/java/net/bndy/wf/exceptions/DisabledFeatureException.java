package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class DisabledFeatureException extends AppException {

    public DisabledFeatureException() {
        super("error.disabledFeature");
    }
}

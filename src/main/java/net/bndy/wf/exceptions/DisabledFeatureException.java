package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class DisabledFeatureException extends AppException {
	private static final long serialVersionUID = 1L;

	public DisabledFeatureException() {
        super("error.disabledFeature");
    }
}

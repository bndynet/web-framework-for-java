package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnanthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoResourceFoundException extends AppException {
    private static final long serialVersionUID = 1L;

    public NoResourceFoundException() {
        super("error.noResourceFound");
    }

    public NoResourceFoundException(String resourceName) {
        super(resourceName);
    }
}
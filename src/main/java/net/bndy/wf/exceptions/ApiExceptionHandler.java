/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.lib.annotation.*;
import net.bndy.wf.lib.ResponseResult;

@RestController
@ControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<?>> handleUnexpectedException(HttpServletRequest req, Exception e)
        throws NoSuchFieldException, SecurityException {

        this.logger.error("{} for {}", e.getMessage(), req.getRequestURL());

        ApiError error = new ApiError(e);

        if (e instanceof NoResourceFoundException) {
            error.setStatus(HttpStatus.NOT_FOUND);
        } else if (e instanceof OAuthException) {
            error.setStatus(HttpStatus.UNAUTHORIZED);
            OAuthException ex = (OAuthException) e;
            error.setMessage(AnnotationHelper
                .getFieldAnnotation(Description.class, OAuthExceptionType.class, ex.getType().name()).value());
        } else {
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return error.toResponse();
    }
}

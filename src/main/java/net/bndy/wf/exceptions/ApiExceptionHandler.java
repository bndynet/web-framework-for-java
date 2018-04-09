/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import javax.servlet.http.HttpServletRequest;

import net.bndy.lib.AnnotationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.lib.ResponseResult;

@RestController
@ControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<?>> handleUnexpectedException(HttpServletRequest req, Exception e) {

        this.logger.error("{} for {}", e.getMessage(), req.getRequestURL());

        ApiError error = new ApiError(e);
        if (e != null) {
            ResponseStatus rs = AnnotationHelper.getClassAnnotation(ResponseStatus.class, e.getClass());
            if (rs != null) {
                error.setStatus(rs.value());
            }
        }
        return error.toResponse();
    }
}

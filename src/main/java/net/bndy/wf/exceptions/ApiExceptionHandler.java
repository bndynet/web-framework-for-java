/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice(annotations = { RestController.class })
public class ApiExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		this.logger.error("{} for {}", e.getMessage(), req.getRequestURL());
		
		return e;
	}
}

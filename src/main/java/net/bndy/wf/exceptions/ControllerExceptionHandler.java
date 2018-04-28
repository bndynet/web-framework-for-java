/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import javax.servlet.http.HttpServletRequest;

import net.bndy.wf.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = { Controller.class })
public class ControllerExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {

		this.logger.error("{} for {}", e.getMessage(), req.getRequestURL());

		// send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", ApplicationContext.language("error.description"));
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);

		if (e instanceof AppException) {
			AppException ex = (AppException) e;
            if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
				mav.addObject("message", e.getMessage());
			}
		}

		return mav;
	}
}

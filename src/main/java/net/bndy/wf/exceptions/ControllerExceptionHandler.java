/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import javax.servlet.http.HttpServletRequest;

import net.bndy.wf.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import net.bndy.wf.lib.annotation.*;

@ControllerAdvice(annotations = { Controller.class })
public class ControllerExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		this.logger.error("{} for {}", e.getMessage(), req.getRequestURL());

		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it - like the OrderNotFoundException example
		// at the start of this post.
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", ApplicationContext.i18n().getString("error.title"));
		if (e != null && e.getMessage() != null && e.getMessage() != "") {
			mav.addObject("message", e.getMessage());
		} else {
			mav.addObject("message", ApplicationContext.i18n().getString("error.description"));
		}
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);

		if (e instanceof OAuthException) {
			OAuthException ex = (OAuthException) e;
			Description d = AnnotationHelper.getFieldAnnotation(Description.class, OAuthExceptionType.class, ex.getType().name());
			if (d != null) {
				mav.addObject("message", d.value());
			} else {
				mav.addObject("message", e.getMessage());
			}
		}

		return mav;
	}
}

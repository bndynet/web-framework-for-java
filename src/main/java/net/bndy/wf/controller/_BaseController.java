/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import net.bndy.wf.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.app.models.User;

@ControllerAdvice
public abstract class _BaseController {

	@Autowired
	protected ApplicationConfig applicationConfig;

	@ModelAttribute("user")
	protected User getCurrentUser() {
		return ApplicationContext.getCurrentUser();
	}

	@ModelAttribute("app")
	protected ApplicationConfig getAppInfo() {
		return this.applicationConfig;
	}
}

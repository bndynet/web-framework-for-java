/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.core.models.User;

import java.util.List;

@ControllerAdvice
public abstract class _BaseController {

	@Autowired
	protected ApplicationConfig applicationConfig;
	@Autowired
	protected ChannelService channelService;

	@ModelAttribute("user")
	protected User getCurrentUser() {
		return ApplicationContext.getCurrentUser();
	}

	@ModelAttribute("app")
	protected ApplicationConfig getAppInfo() {
		return this.applicationConfig;
	}

	@ModelAttribute("cms_menus")
	protected List<Channel> cmsChannels() {
		return this.channelService.getTree(false);
	}
}

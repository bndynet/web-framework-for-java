/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.app.models.User;

public abstract class _BaseController {

	protected User getCurrentUser() {
		return ApplicationContext.getCurrentUser();
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends _BaseController {

	@RequestMapping(value = "/index")
	public String home(Model model) {
		model.addAttribute("who", this.getCurrentUser() != null ? this.getCurrentUser().getUsername() : "Unknown User");
		return "admin/index";
	}

	@RequestMapping(value = "/applications")
	public String applications(Model model) {
		return "admin/applications";
	}
	
	@RequestMapping(value = "/users")
	public String users(Model model) {
		return "admin/users";
	}
}

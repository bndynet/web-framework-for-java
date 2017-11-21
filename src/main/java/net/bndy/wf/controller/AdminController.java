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

	@RequestMapping(value = "/")
	public String home(Model model) {
		model.addAttribute("who", this.getCurrentUser() != null ? this.getCurrentUser().getUsername() : "Unknown User");
		return "admin/index";
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bndy.wf.modules.app.models.Client;
import net.bndy.wf.modules.app.services.repositories.ClientRepository;

@Controller
@RequestMapping(value = "/admin")
public class AdminController extends _BaseController {

	@Autowired
	ClientRepository clientRepository;

	@RequestMapping(value = "/index")
	public String home(Model model) {
		model.addAttribute("who", this.getCurrentUser() != null ? this.getCurrentUser().getUsername() : "Unknown User");
		return "admin/index";
	}

	@RequestMapping(value = "/applications")
	public String applications(Model model) {
		List<Client> list = this.clientRepository.findAll();
		model.addAttribute("list", list);
		return "admin/applications";
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import javax.servlet.http.HttpSession;

import net.bndy.wf.exceptions.AbnormalAccountException;
import net.bndy.wf.modules.core.models.User;
import net.bndy.wf.modules.core.services.UserService;
import net.bndy.wf.modules.core.services.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.bndy.wf.modules.core.services.SecurityService;

@Controller
@RequestMapping("/sso")
public class SsoController extends _BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model, HttpSession session) {
		model.addAttribute("model", new User());
		if(!this.userService.hasUsers()) {
			model.addAttribute("admin", true);
		}
		return "sso/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute("model") User user, BindingResult bindingResult, Model model) throws AbnormalAccountException {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "sso/signup";
		}

		user = userService.save(user);
		
		if (!user.isEnabled()) {
			throw new AbnormalAccountException("error.disabledUser");
		}
		if (user.isLocked()) {
			throw new AbnormalAccountException("error.lockedUser");
		}
		if (user.isExpired()) {
			throw new AbnormalAccountException("error.expiredUser");
		}

		securityService.autologin(user.getUsername(), user.getPasswordConfirm());

		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout, String redirect_uri, HttpSession session) {
		// create first user
		if(!this.userService.hasUsers()) {
			return "redirect:/sso/signup";
		}
		
		return "sso/login";
	}
}

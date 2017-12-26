/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.bndy.wf.config.Constant;
import net.bndy.wf.exceptions.OAuthException;
import net.bndy.wf.exceptions.OAuthExceptionType;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.*;
import net.bndy.wf.modules.core.OAuthService;
import net.bndy.wf.modules.core.SecurityService;

@Controller
@RequestMapping("/sso")
public class SsoController extends _BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private OAuthService oauthService;
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
	public String signup(@ModelAttribute("model") User user, BindingResult bindingResult, Model model) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "sso/signup";
		}

		userService.save(user);

		securityService.autologin(user.getUsername(), user.getPasswordConfirm());

		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout, String redirect_uri, HttpSession session) {
		// create first user
		if(!this.userService.hasUsers()) {
			return "redirect:/sso/signup";
		}
		
		// skip if user logged in
		if (this.oauthService.getCurrentUser() != null) {
			return "redirect:/sso/authorize";
		}

		if (redirect_uri != null && !"".equals(redirect_uri)) {
			session.setAttribute(Constant.OAUTH_REDIRECT_KEY, redirect_uri);
		}

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("logout", "You have been logged out successfully.");

		return "sso/login";
	}
}

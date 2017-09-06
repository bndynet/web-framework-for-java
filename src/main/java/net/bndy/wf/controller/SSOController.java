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

import net.bndy.wf.Constant;
import net.bndy.wf.exceptions.OAuthException;
import net.bndy.wf.exceptions.OAuthExceptionType;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.*;

@Controller
@RequestMapping("/sso")
public class SSOController {

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
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute("model") User user, BindingResult bindingResult, Model model) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		userService.save(user);

		securityService.autologin(user.getUsername(), user.getPasswordConfirm());

		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout, String redirect_uri, HttpSession session) {
		// skip if user logged in
		if (this.oauthService.getCurrentUser() != null) {
			return "redirect:/sso/authorize";
		}

		if (redirect_uri != null && !"".equals(redirect_uri)) {
			session.setAttribute(Constant.KEY_OAUTH_REDIRECT, redirect_uri);
		}

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("logout", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(Model model, HttpSession session) throws OAuthException {
		String clientId = session.getAttribute(Constant.KEY_OAUTH_CLIENTID).toString();

		// skip if current user has been authorized
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = this.userService.findByUsername(auth.getName());
			if (user != null) {
				ClientUser cu = this.oauthService.getAuthInfo(clientId, user.getId());
				if (cu != null) {
					cu = this.oauthService.refreshAuthCode(cu);
					return "redirect:" + this.oauthService.getRedirectUri(session, cu.getAuthorizationCode());
				}
			}
		}

		String scope = session.getAttribute(Constant.KEY_OAUTH_SCOPE).toString();
		model.addAttribute("scope", scope);

		Client client = this.oauthService.getClient(clientId);
		if (client != null) {
			model.addAttribute("app", client);
			model.addAttribute("redirectUri", session.getAttribute(Constant.KEY_OAUTH_REDIRECT));
		} else {
			session.removeAttribute(Constant.KEY_OAUTH_CLIENTID);
			session.removeAttribute(Constant.KEY_OAUTH_REDIRECT);
			session.removeAttribute(Constant.KEY_OAUTH_SCOPE);
			model.addAttribute("error", OAuthExceptionType.InvalidClientIDOrRedirectUri.toString());
		}
		return "authorize";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String postAuthorize(HttpSession session) throws OAuthException {
		String scope = session.getAttribute(Constant.KEY_OAUTH_SCOPE).toString();
		String clientId = session.getAttribute(Constant.KEY_OAUTH_CLIENTID).toString();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && clientId != null && clientId != "") {
			User user = this.userService.findByUsername(auth.getName());

			if (user != null) {
				ClientUser cu = this.oauthService.authorize(user.getId(), clientId, scope);
				return "redirect:" + this.oauthService.getRedirectUri(session, cu.getAuthorizationCode());
			}
		}

		return "/authroize?error";
	}
}

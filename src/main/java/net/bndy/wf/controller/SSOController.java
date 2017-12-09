/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import net.bndy.wf.Constant;
import net.bndy.wf.exceptions.OAuthException;
import net.bndy.wf.exceptions.OAuthExceptionType;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.*;
import net.bndy.wf.modules.core.OAuthService;
import net.bndy.wf.modules.core.SecurityService;

@Controller
@RequestMapping("/sso")
public class SSOController extends _BaseController {

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

	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String authorize(Model model, HttpSession session) throws OAuthException {
		String clientId = session.getAttribute(Constant.OAUTH_CLIENTID_KEY).toString();

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

		String scope = session.getAttribute(Constant.OAUTH_SCOPE_KEY).toString();
		model.addAttribute("scope", scope);

		Client client = this.oauthService.getClient(clientId);
		if (client != null) {
			model.addAttribute("app", client);
			model.addAttribute("redirectUri", session.getAttribute(Constant.OAUTH_REDIRECT_KEY));
		} else {
			session.removeAttribute(Constant.OAUTH_CLIENTID_KEY);
			session.removeAttribute(Constant.OAUTH_REDIRECT_KEY);
			session.removeAttribute(Constant.OAUTH_SCOPE_KEY);
			model.addAttribute("error", OAuthExceptionType.InvalidClientIDOrRedirectUri.toString());
		}
		return "sso/authorize";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String postAuthorize(HttpSession session) throws OAuthException {
		String scope = session.getAttribute(Constant.OAUTH_SCOPE_KEY).toString();
		String clientId = session.getAttribute(Constant.OAUTH_CLIENTID_KEY).toString();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && clientId != null && clientId != "") {
			User user = this.userService.findByUsername(auth.getName());

			if (user != null) {
				ClientUser cu = this.oauthService.authorize(user.getId(), clientId, scope);
				return "redirect:" + this.oauthService.getRedirectUri(session, cu.getAuthorizationCode());
			}
		}

		return "sso/authroize?error";
	}
}

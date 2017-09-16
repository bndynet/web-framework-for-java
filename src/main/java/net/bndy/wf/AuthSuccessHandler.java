/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private RequestCache requestCache;

	public AuthSuccessHandler() {
		super();
		this.requestCache = new HttpSessionRequestCache();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// oauth login
		String clientId = (String) request.getSession().getAttribute(Constant.OAUTH_CLIENTID_KEY);
		if (clientId != null) {
			// go to authorization page
			this.redirectStrategy.sendRedirect(request, response, "/sso/authorize");
			return;
		}

		// normal login and redirect to back url
		String redirect = (String) request.getSession().getAttribute(Constant.OAUTH_REDIRECT_KEY);
		if (redirect == null || "".equals(redirect)) {
			SavedRequest savedRequest = this.requestCache.getRequest(request, response);
			if (savedRequest != null) {
				redirect = savedRequest.getRedirectUrl();
			} else {
				redirect = this.getDefaultTargetUrl();
			}
		}

		this.redirectStrategy.sendRedirect(request, response, redirect);
	}
}

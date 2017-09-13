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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public AuthSuccessHandler() {
		super();
	}

	public AuthSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// oauth login
		String clientId = (String) request.getSession().getAttribute(Constant.KEY_OAUTH_CLIENTID);
		if (clientId != null) {
			// go to authorization page
			this.redirectStrategy.sendRedirect(request, response, "/sso/authorize");
			return;
		}

		// normal login and redirect to back url
		String redirect = (String) request.getSession().getAttribute(Constant.KEY_OAUTH_REDIRECT);
		if (redirect == null || "".equals(redirect)) {
			redirect = this.getDefaultTargetUrl();
		}

		this.redirectStrategy.sendRedirect(request, response, redirect);
	}
}

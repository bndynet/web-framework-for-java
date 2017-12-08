/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.Constant;
import net.bndy.wf.exceptions.OAuthException;
import net.bndy.wf.exceptions.OAuthExceptionType;
import net.bndy.wf.modules.app.models.ClientUser;
import net.bndy.wf.modules.app.models.User;
import net.bndy.wf.modules.app.services.UserService;
import net.bndy.wf.modules.core.OAuthService;
import net.bndy.wf.modules.core.TokenInfo;

@Api(value = "Authorization and authentication")
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {

	@Autowired
	UserService userService;
	@Autowired
	OAuthService oauthService;
	
	@ApiOperation(value = "Get authorization code by client id")
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public void authorize(HttpServletResponse response, @RequestParam(name = "response_type") String responseType,
			@RequestParam(name = "client_id") String clientId, @RequestParam(name = "redirect_uri") String redirectUri,
			@RequestParam(name = "scope") String scope, HttpSession session) throws IOException, OAuthException {
		session.setAttribute(Constant.OAUTH_CLIENTID_KEY, clientId);
		session.setAttribute(Constant.OAUTH_REDIRECT_KEY, redirectUri);
		session.setAttribute(Constant.OAUTH_SCOPE_KEY, scope);
		
		// validate client id
		if (!this.oauthService.verifyClient(clientId, redirectUri))  {
			throw new OAuthException(OAuthExceptionType.InvalidClientIDOrRedirectUri);
		}
		
		User user = this.oauthService.getCurrentUser();
		if (user != null) {
			ClientUser cu = this.oauthService.getAuthInfo(clientId, user.getId());
			if (cu != null) {
				cu = this.oauthService.refreshAuthCode(cu);
				response.sendRedirect(this.oauthService.getRedirectUri(session, cu.getAuthorizationCode()));
				return;
			}
		}

		response.sendRedirect("/sso/login");
	}

	@ApiOperation(value = "Get access token by authorization code")
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public TokenInfo token(@RequestParam(name = "client_id") String clientId,
			@RequestParam(name = "client_secret") String clientSecret,
			@RequestParam(name = "grant_type") String grantType, @RequestParam(name = "code") String code,
			@RequestParam(name = "redirect_uri") String redirectUri) {

		return new TokenInfo();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		User u = (User) this.userService.login(username, password);
		if (u != null) {
			User responseUser = new User();
			responseUser.setUsername(u.getUsername());
			responseUser.setCreateDate(u.getCreateDate());
			responseUser.setId(u.getId());
			responseUser.setLastUpdate(u.getLastUpdate());
			return responseUser;
		} else {
			throw new OAuthException(OAuthExceptionType.InvalidUser);
		}
	}

    @ApiOperation(value = "Gets current user")
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public HashMap<String, Object> me(HttpServletRequest request) throws MalformedURLException {
		User u = ApplicationContext.getCurrentUser();
        if (u != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", u.getUsername());
            map.put("avatar", new URL(new URL(request.getRequestURL().toString()), u.getAvatar()).toString());
            map.put("roles", u.getRoles());
            return map;
        }
        return null;
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.services;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.Constant;
import net.bndy.wf.exceptions.OAuthException;
import net.bndy.wf.exceptions.OAuthExceptionType;
import net.bndy.wf.lib.Utils;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.repositories.*;

@Service
@Transactional
public class OAuthService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ClientUserRepository clientUserRepository;

	@Autowired
	UserService userService;

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return this.userRepository.findByUsername(auth.getName());
		}
		return null;
	}

	public String newAuthCode() {
		return Utils.generateRandomString(Constant.LEN_AUTH_CODE);
	}

	public ClientUser refreshAuthCode(String clientId, long userId) {
		String authCode = this.newAuthCode();
		ClientUser cu = this.clientUserRepository.findByUserIdAndClientId(userId, clientId);
		if (cu != null) {
			cu.setAuthorizationCode(authCode);
			cu = this.clientUserRepository.saveAndFlush(cu);
		}

		return cu;
	}

	public ClientUser refreshAuthCode(ClientUser cu) {
		if (cu != null) {
			String authCode = this.newAuthCode();
			cu.setAuthorizationCode(authCode);
			cu = this.clientUserRepository.saveAndFlush(cu);
		}
		return cu;
	}

	public boolean verifyRedirectUri(String clientId, String redirectUri) {
		if (clientId == null || "".equals(clientId) || redirectUri == null || "".equals(redirectUri)) {
			return false;
		}

		Client c = this.clientRepository.findByClientId(clientId);
		if (c != null && c.getRedirectUri() != null && !"".equals(c.getRedirectUri())) {
			if (Arrays.asList(c.getRedirectUri().toLowerCase().split(";")).indexOf(redirectUri.toLowerCase()) >= 0) {
				return true;
			}
		}
		return false;
	}

	public String getRedirectUri(HttpSession session, String authCode) throws OAuthException {
		String clientId = (String) session.getAttribute(Constant.KEY_OAUTH_CLIENTID);
		String redirectUri = (String) session.getAttribute(Constant.KEY_OAUTH_REDIRECT);

		if (clientId != null && redirectUri != null) {
			if (this.verifyRedirectUri(clientId, redirectUri)) {
				session.removeAttribute(Constant.KEY_OAUTH_CLIENTID);
				session.removeAttribute(Constant.KEY_OAUTH_REDIRECT);
				session.removeAttribute(Constant.KEY_OAUTH_SCOPE);

				redirectUri = String.format("%s?code=%s", redirectUri, authCode);
				return redirectUri;
			}
		}

		throw new OAuthException(OAuthExceptionType.InvalidRedirectUri);
	}

	public void login(String username, String password, String clientId) {
		User user = this.userService.login(username, password);
		if (user != null) {

			ClientUser cu = this.clientUserRepository.findByUserIdAndClientId(user.getId(), clientId);
			if (cu != null) {
				cu = this.refreshAuthCode(cu);
			} else {
				// TODO: throw 401
			}
		}
	}

	public ClientUser authorize(long userId, String clientId, String scope) {
		ClientUser cu = this.clientUserRepository.findByUserIdAndClientId(userId, clientId);
		if (cu != null) {
			cu.setScope(scope);
		} else {
			cu = new ClientUser();
			cu.setUserId(userId);
			cu.setClientId(clientId);
			cu.setScope(scope);
		}
		cu.setAuthorizationCode(this.newAuthCode());
		cu = this.clientUserRepository.saveAndFlush(cu);
		return cu;
	}

	public ClientUser getToken(String authorizationCode) {
		ClientUser cu = this.clientUserRepository.findByAuthorizationCode(authorizationCode);
		if (cu != null) {
			// TODO: generate tokens
			cu.setAccessToken("accessToken");
			cu.setRefreshToken("refreshToken");
			cu = this.clientUserRepository.saveAndFlush(cu);
			return cu;
		}

		return null;
	}

	public Client getClient(String clientId) {
		return this.clientRepository.findByClientId(clientId);
	}

	public ClientUser getAuthInfo(String clientId, long userId) {
		return this.clientUserRepository.findByUserIdAndClientId(userId, clientId);
	}
}

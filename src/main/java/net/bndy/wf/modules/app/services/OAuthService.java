package net.bndy.wf.modules.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public void login(String username, String password, String clientId) {
		User user = this.userService.login(username, password);
		if (user != null) {
			String authorizationCode = "TestAuthCode";

			ClientUser cu = this.clientUserRepository.findByUserIdAndClientId(user.getId(), clientId);
			if (cu != null) {
				cu.setAuthorizationCode(authorizationCode);
				cu = this.clientUserRepository.saveAndFlush(cu);
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
}

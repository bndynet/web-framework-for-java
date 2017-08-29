package net.bndy.wf.modules.app.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.exceptions.UnanthorizedException;
import net.bndy.wf.modules.app.models.TokenInfo;
import net.bndy.wf.modules.app.models.User;
import net.bndy.wf.modules.app.services.UserService;

@Api(value = "Authorization and authentication")
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "Get authorization code by client id")
	@RequestMapping(value="/authorize", method = RequestMethod.GET)
	public void authorize(HttpServletResponse response,
			@RequestParam(name="response_type") String responseType,
			@RequestParam(name="client_id") String clientId,
			@RequestParam(name="redirect_uri") String redirectUri,
			@RequestParam(name="scope") String scope) throws IOException {
		
		response.sendRedirect(redirectUri);
	}

	@ApiOperation(value = "Get access token by authorization code")
	@RequestMapping(value="/token", method = RequestMethod.POST)
	public TokenInfo token(
			@RequestParam(name="client_id") String clientId,
			@RequestParam(name="client_secret") String clientSecret,
			@RequestParam(name="grant_type") String grantType,
			@RequestParam(name="code") String code,
			@RequestParam(name="redirect_uri") String redirectUri) {
		
		return new TokenInfo();
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public User login(@RequestParam(name="username") String username, @RequestParam(name="password") String password) {
		User u = (User) this.userService.login(username, password);
		if(u != null) {
			User responseUser = new User();
			responseUser.setUsername(u.getUsername());
			responseUser.setCreateDate(u.getCreateDate());
			responseUser.setId(u.getId());
			responseUser.setLastUpdate(u.getLastUpdate());
			return responseUser;
		} else {
			throw new UnanthorizedException();
		}
	}
}

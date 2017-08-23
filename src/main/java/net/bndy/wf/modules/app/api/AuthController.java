package net.bndy.wf.modules.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.bndy.wf.lib.UnanthorizedException;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.User;
import net.bndy.wf.modules.app.services.UserService;

@Api(value = "Authorization and authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends _BaseApi<User> {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public User login(@RequestParam(name="username") String username, @RequestParam(name="password") String password) {
		User u = (User) this.userService.login(username, password);
		if(u != null) {
			User responseUser = new User();
			responseUser.setUserName(u.getUserName());
			responseUser.setCreateDate(u.getCreateDate());
			responseUser.setId(u.getId());
			responseUser.setLastUpdate(u.getLastUpdate());
			return responseUser;
		} else {
			throw new UnanthorizedException();
		}
	}
}

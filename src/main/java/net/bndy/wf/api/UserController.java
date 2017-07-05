package net.bndy.wf.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.domain.User;
import net.bndy.wf.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<User> getAll() {
		return userService.getAll();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
}

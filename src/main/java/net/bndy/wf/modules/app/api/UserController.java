package net.bndy.wf.modules.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public Page<User> getAll(
			@PageableDefault(size = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return userService.getUsers(pageable);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
}

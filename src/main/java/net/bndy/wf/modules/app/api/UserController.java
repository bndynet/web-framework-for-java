/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.UserService;

@Api(value = "User API")
@RestController
@RequestMapping("/api/v1/app/users")
public class UserController extends _BaseApi<User> {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Get all authorized apps for current user")
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public List<ClientUser> getClients() {
		return this.userService.getClients(this.getCurrentUser().getId());
	}

	@ApiOperation(value = "Cancel authorization for specified app")
	@RequestMapping(value = "/removeapp", method = RequestMethod.PUT)
	public void removeClient(@RequestParam(name = "clientId") String clientId) {
		if (this.getCurrentUser() != null) {
			this.userService.removeClient(clientId, this.getCurrentUser().getId());
		}
	}

	@ApiOperation(value = "Enable or disable user")
	@RequestMapping(value = "/{id}/toggleenabled", method = RequestMethod.PUT)
	public void toggleEnabled(@PathVariable(name = "id") long id) {
		User user = this.userService.get(id);
		if (user != null) {
			user.setEnabled(!user.isEnabled());
			this.userService.save(user);
		}
	}

	@ApiOperation(value = "Change role for user")
	@RequestMapping(value = "/{id}/changerole", method = RequestMethod.PUT)
	public void changeRole(@PathVariable(name = "id") long Id, @RequestParam(name = "roleId") long roleId) {
        this.userService.changeRole(Id, roleId);
	}
}

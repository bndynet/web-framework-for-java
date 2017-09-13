/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.UserService;

@Api(value = "User API")
@RestController
@RequestMapping("/api/v1/app/users")
public class UserController extends _BaseApi<User> {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public List<ClientUser> getClients() {
		return this.userService.getClients(this.getCurrentUser().getId());
	}

	@RequestMapping(value = "/removeapp", method = RequestMethod.PUT)
	public void removeClient(@RequestParam(name = "clientId") String clientId) {
		if (this.getCurrentUser() != null) {
			this.userService.removeClient(clientId, this.getCurrentUser().getId());
		}
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.api;

import net.bndy.wf.modules.core.models.OauthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.core.models.Client;
import net.bndy.wf.modules.core.services.ClientService;

@Api(value = "Registered Applications API")
@RestController
@RequestMapping({"/api/core/clients", "/api/v1/core/clients"})
public class ClientController extends _BaseApi<Client> {

	@Autowired
	private ClientService clientService;

	@Override
	public Client post(@RequestBody Client entity) {
		if (entity.getDetails() == null) {
			entity.setDetails(new OauthClientDetails());
		}
		return this.clientService.saveClient(entity.getId(), entity.getName(), entity.getIcon(),
			entity.getDetails().getWebServerRedirectUri(),
			entity.getDetails().getScope()
		);
	}

	@Override
	public Client put(@PathVariable(name = "id") long id, @RequestBody Client entity) {
		if (entity.getDetails() == null) {
			entity.setDetails(new OauthClientDetails());
		}
		return this.clientService.saveClient(id, entity.getName(), entity.getIcon(),
			entity.getDetails().getWebServerRedirectUri(),
			entity.getDetails().getScope()
		);
	}
}

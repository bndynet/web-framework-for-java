package net.bndy.wf.modules.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.Client;
import net.bndy.wf.modules.app.services.ClientService;;

@Api(value = "Registered Applications API")
@RestController
@RequestMapping("/api/v1/app/clients")
public class ClientController extends _BaseApi<Client> {

	@Autowired
	private ClientService clientService;

	@Override
	public Client post(@RequestBody Client entity) {
		return this.clientService.registerApplication(entity.getName(), entity.getIcon(), entity.getRedirectUri());
	}

	@Override
	public Client put(@PathVariable(name = "id") long id, @RequestBody Client entity) {
		entity.setId(id);
		return this.clientService.updateApplication(entity);
	}

	@Override
	public void delete(@PathVariable(name="id") long id) {
		this.clientService.delete(id);
	}
}

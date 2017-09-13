package net.bndy.wf.modules.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.Constant;
import net.bndy.wf.lib.StringHelper;
import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.app.models.Client;
import net.bndy.wf.modules.app.services.repositories.ClientRepository;
import net.bndy.wf.modules.app.services.repositories.ClientUserRepository;

@Service
@Transactional
public class ClientService extends _BaseService<Client> {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientUserRepository clientUserRepository;

	public Client registerApplication(String name, String icon, String redirectUri) {
		Client client = new Client();
		client.setName(name);
		client.setIcon(icon);
		client.setRedirectUri(redirectUri);
		client.setClientId(StringHelper.generateRandomString(Constant.LEN_CLIENT_ID));
		client.setClientSecret(StringHelper.generateRandomString(Constant.LEN_CLIENT_SECRET));

		return this.clientRepository.saveAndFlush(client);
	}

	public Client updateApplication(Client client) {
		Client entity = this.clientRepository.findOne(client.getId());
		if (entity != null) {
			entity.setName(client.getName());
			entity.setIcon(client.getIcon());
			entity.setRedirectUri(client.getRedirectUri());

			if (entity.getClientId() == null || "".equals(entity.getClientId())) {
				entity.setClientId(StringHelper.generateRandomString(Constant.LEN_CLIENT_ID));
				entity.setClientSecret(StringHelper.generateRandomString(Constant.LEN_CLIENT_SECRET));
			}
			entity = this.clientRepository.saveAndFlush(entity);
		}
		return entity;
	}

	@Override
	public boolean delete(long id) {
		Client client = this.clientRepository.findOne(id);
		if (client != null) {
			this.clientUserRepository.deleteByClientId(client.getClientId());
		}
		return super.delete(id);
	}
}

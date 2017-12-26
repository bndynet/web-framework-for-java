package net.bndy.wf.modules.app.services;

import net.bndy.wf.modules.core.models.OauthClientDetails;
import net.bndy.wf.modules.core.services.repositories.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.config.Constant;
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
    private OauthClientDetailsRepository oauthClientDetailsRepository;
    @Autowired
    private ClientUserRepository clientUserRepository;

    public Client findByClientId(String clientId) {
        OauthClientDetails details = oauthClientDetailsRepository.findByClientId(clientId);
        if (details != null) {
            return details.getAppClient();
        }
        return null;
    }

    public Client saveClient(Long appClientId, String name, String icon, String redirectUri, String scope) {
        Client client = null;
        if (appClientId != null) {
            client = clientRepository.findOne(appClientId);
        }
        if (client == null) {
            client = new Client();
        }

        client.setName(name);
        client.setIcon(icon);

        OauthClientDetails details = client.getDetails();
        if (details == null) {
            details = new OauthClientDetails();
        }
        details.setScope(scope);
        details.setWebServerRedirectUri(redirectUri);

        if (details.getClientId() == null || details.getClientId() == "") {
            details.setClientId(StringHelper.generateRandomString(Constant.CLIENT_ID_LEN));
        }
        if (details.getClientSecret() == null || details.getClientSecret() == "") {
            details.setClientSecret(StringHelper.generateRandomString(Constant.CLIENT_SECRET_LEN));
        }
        client = this.clientRepository.saveAndFlush(client);
        details.setAppClientId(client.getId());
        this.oauthClientDetailsRepository.saveAndFlush(details);
        return client;
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services;

import net.bndy.wf.modules.oauth.models.OauthClientDetails;
import net.bndy.wf.modules.oauth.repositories.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.lib.StringHelper;
import net.bndy.wf.config.Constant;
import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.core.models.Client;
import net.bndy.wf.modules.core.services.repositories.ClientRepository;

@Service
@Transactional
public class ClientService extends _BaseService<Client> {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    public Client findByClientId(String clientId) {
        OauthClientDetails details = oauthClientDetailsRepository.findByClientId(clientId);
        if (details != null) {
            return details.getClient();
        }
        return null;
    }


    public Client saveClient(Long appClientId, String name, String icon, String redirectUri, String scope) {
        return saveClient(appClientId, name, icon, redirectUri, scope, null, null);
    }

    public Client saveClient(Long appClientId, String name, String icon, String redirectUri, String scope, String clientId, String clientSecret) {
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

        if(details.getAccessTokenValidity() == null || details.getAccessTokenValidity() == 0) {
            details.setAccessTokenValidity(Constant.CLIENT_TOKEN_EXPIRE_IN);
        }
        if(details.getRefreshTokenValidity() == null || details.getRefreshTokenValidity() == 0) {
            details.setRefreshTokenValidity(Constant.CLIENT_TOKEN_EXPIRE_IN);
        }
        if (details.getAuthorizedGrantTypes() == null || details.getAuthorizedGrantTypes() == "") {
            details.setAuthorizedGrantTypes("authorization_code");
        }
        if (details.getClientId() == null || details.getClientId() == "") {
            if (clientId == null || clientId == "") {
                details.setClientId(StringHelper.generateRandomString(Constant.CLIENT_ID_LEN));
            } else {
                details.setClientId(clientId);
            }
        }
        if (details.getClientSecret() == null || details.getClientSecret() == "") {
            if (clientSecret == null || clientSecret == "") {
                details.setClientSecret(StringHelper.generateRandomString(Constant.CLIENT_SECRET_LEN));
            } else {
                details.setClientSecret(clientSecret);
            }
        }
        client = this.clientRepository.saveAndFlush(client);
        details.setCoreClientId(client.getId());
        this.oauthClientDetailsRepository.saveAndFlush(details);
        return client;
    }
}

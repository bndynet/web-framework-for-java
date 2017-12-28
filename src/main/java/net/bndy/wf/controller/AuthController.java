/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import net.bndy.wf.modules.core.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@Controller
@SessionAttributes(types = AuthorizationRequest.class)
@RequestMapping(value = "/oauth")
public class AuthController {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/confirm_access")
    public String oauthConfirmAccess(
        @ModelAttribute AuthorizationRequest authRequest,
        Model viewModel
    ) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authRequest.getClientId());
        viewModel.addAttribute("authRequest", authRequest);
        viewModel.addAttribute("client", clientDetails);
        if(!authRequest.getClientId().isEmpty()) {
            viewModel.addAttribute("clientApp", clientService.findByClientId(clientDetails.getClientId()));
        }
        viewModel.addAttribute("scopes", String.join(",", authRequest.getScope()));
        return "auth/confirm_access";
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.config;

import net.bndy.wf.modules.core.models.User;
import net.bndy.wf.modules.core.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Memory Example
//        clients.inMemory()
//            .withClient("client_id")
//            .secret("client_secret")
//            .authorizedGrantTypes("implicit", "client_credentials", "password", "authorization_code", "refresh_token")
//            .scopes("read", "write")
//            .authorizedGrantTypes("password","authorization_code", "refresh_token");

        clients.jdbc(dataSource);   // require table named oauth_client_details
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .tokenEnhancer(new AppTokenEnhancer())
            // require tables oauth_access_token, oauth_client_token, oauth_code, oauth_refresh_token
            .tokenStore(new JdbcTokenStore(dataSource))
            .approvalStore(new JdbcApprovalStore(dataSource))
        ;
    }


    public class AppTokenEnhancer implements TokenEnhancer {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
            User user = (User) oAuth2Authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>();

            additionalInfo.put("id", user.getId());
            additionalInfo.put("username", user.getUsername());
            additionalInfo.put("avatar", user.getAvatar());
            additionalInfo.put("authorities", user.getAuthorities());

            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

            return oAuth2AccessToken;
        }
    }

    @Configuration
    @EnableResourceServer
    public class ResourceServer extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/v1/**").authorizeRequests()
                .anyRequest().authenticated();
        }
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.oauth.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

import java.sql.Blob;

@Entity
@Table(name = "oauth_access_token")
public class OauthAccessToken extends _BaseEntity {
    private String tokenId;
    private Blob token;
    private String authenticationId;
    private String userName;
    private String clientId;
    private Blob authentication;
    private String refreshToken;
}

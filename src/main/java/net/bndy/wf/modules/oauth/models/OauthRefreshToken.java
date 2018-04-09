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
@Table(name = "oauth_refresh_token")
public class OauthRefreshToken extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String tokenId;
    private Blob token;
    private Blob authentication;

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Blob getToken() {
		return token;
	}
	public void setToken(Blob token) {
		this.token = token;
	}
	public Blob getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Blob authentication) {
		this.authentication = authentication;
	}
}

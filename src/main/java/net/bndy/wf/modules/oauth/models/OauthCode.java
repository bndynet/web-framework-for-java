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
@Table(name = "oauth_code")
public class OauthCode extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String code;
    private Blob authentication;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Blob getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Blob authentication) {
		this.authentication = authentication;
	}
}

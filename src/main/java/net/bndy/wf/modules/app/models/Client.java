package net.bndy.wf.modules.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name = "app_client")
public class Client extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}

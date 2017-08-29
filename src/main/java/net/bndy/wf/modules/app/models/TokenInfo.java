package net.bndy.wf.modules.app.models;

import java.io.Serializable;

public class TokenInfo  implements Serializable {

	private static final long serialVersionUID = 1L;

	private long expiresIn;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
	private long refreshTokenExpiresIn;

	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public long getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}
	public void setRefreshTokenExpiresIn(long refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}
}

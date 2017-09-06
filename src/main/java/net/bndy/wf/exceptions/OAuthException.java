/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

public class OAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private OAuthExceptionType type;
	
	public OAuthException(OAuthExceptionType type) {
		this.type = type;
	}

	public OAuthExceptionType getType() {
		return type;
	}

	public void setType(OAuthExceptionType type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		return this.type.toString();
	}

}

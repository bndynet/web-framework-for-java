/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class OAuthException extends Exception {

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
	
	
}

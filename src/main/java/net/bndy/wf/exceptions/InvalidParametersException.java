/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ArrayList<String> Parameters = new ArrayList<String>();
	
	public InvalidParametersException(String...parameters) {
		for(String p: parameters) {
			this.Parameters.add(p);
		}
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
}

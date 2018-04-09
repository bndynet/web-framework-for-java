/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends AppException {
	private static final long serialVersionUID = 1L;

	public ArrayList<String> Parameters = new ArrayList<String>();

	public InvalidParametersException(String...parameters) {
		super("error.invalidParameters");
		for(String p: parameters) {
			this.Parameters.add(p);
		}
	}

	@Override
	public String getMessage() {
		return super.getMessage() + String.join(",", this.Parameters);
	}
}

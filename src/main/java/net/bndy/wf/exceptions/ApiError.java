/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import net.bndy.wf.lib.ResponseResult;
import org.springframework.http.HttpStatus;

public class ApiError extends ResponseResult<Exception> {

	public ApiError(Exception data) {
		super(data);
		this.setStatus(HttpStatus.BAD_REQUEST);
		this.setMessage(data.getMessage());
	}
}

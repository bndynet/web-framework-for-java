/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.exceptions;

import org.springframework.http.HttpStatus;

import net.bndy.wf.lib.ResponseResult;

public class ApiError extends ResponseResult<Exception> {

	private String title;

	public String getTitle() {
		return title;
	}


	public ApiError(Exception data) {
		super(data);

		this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		this.setMessage(data.getMessage());

		if (data instanceof AppException) {
			this.title = ((AppException)data).getTitle();
		}
	}
}

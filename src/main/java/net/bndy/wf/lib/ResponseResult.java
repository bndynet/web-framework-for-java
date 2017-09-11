/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseResult<T> {

	private HttpStatus status = HttpStatus.OK;
	private String message;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date date;
	private T data;

	private ResponseResult() {
		this.date = new Date();
	}

	public ResponseResult(String message) {
		this();
		this.message = message;
	}

	public ResponseResult(T data) {
		this();
		this.setData(data);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getStatusCode() {
		return this.getStatus().value();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResponseEntity<ResponseResult<?>> toResponse() {
		ResponseEntity<ResponseResult<?>> responseEntity = new ResponseEntity<ResponseResult<?>>(this,
				this.getStatus());
		return responseEntity;
	}
}

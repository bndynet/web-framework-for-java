/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

public class AppException extends AppResponseResult {

	public AppException(String message, Exception e) {
		super(message, AppResponseResultStatus.Error, e);
	}
	
	public AppException(String message){
		super(message, AppResponseResultStatus.Error, null);
	}
	
	public AppException(Exception e){
		super(e.getMessage(), AppResponseResultStatus.Error, e);
	}
}

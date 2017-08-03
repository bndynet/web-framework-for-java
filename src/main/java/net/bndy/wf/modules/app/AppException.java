package net.bndy.wf.modules.app;

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

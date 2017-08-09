package net.bndy.wf.lib;

public enum AppResponseResultStatus {
	OK(200), 
	Error(-500)
	;
	
	private int code;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private AppResponseResultStatus(int code){
		this.code = code;
	}
}

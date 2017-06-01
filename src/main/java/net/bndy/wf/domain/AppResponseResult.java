package net.bndy.wf.domain;

public class AppResponseResult {
	private String title;
	private AppResponseResultStatus status;
	private Object data;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public AppResponseResultStatus getStatus() {
		return status;
	}
	public void setStatus(AppResponseResultStatus status) {
		this.status = status;
	}
	public int getStatusCode(){
		return this.status.getCode();
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public AppResponseResult(String title, AppResponseResultStatus status, Object data){
		this.title = title;
		this.status = status;
		this.data = data;
	}
}

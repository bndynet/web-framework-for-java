package net.bndy.wf.domain;

public class Page extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private long boTypeId;
	private String title;
	private String content;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getBoTypeId() {
		return boTypeId;
	}
	public void setBoTypeId(long boTypeId) {
		this.boTypeId = boTypeId;
	}

}

package net.bndy.wf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Page extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private long boTypeId;
	private String title;
	@Column(columnDefinition="TEXT")
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

package net.bndy.wf.domain;

import javax.persistence.Entity;

@Entity
public class Comment extends _BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private long boId;
	private long userId;
	private String title;
	private String content;

	public long getBoId() {
		return boId;
	}
	public void setBoId(long boId) {
		this.boId = boId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
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
}

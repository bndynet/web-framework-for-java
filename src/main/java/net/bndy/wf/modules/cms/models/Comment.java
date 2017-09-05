/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="cms_comment")
public class Comment extends _BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private long boId;
	private long boTypeId;
	private String content;
	private String title;
	private long userId;
	public long getBoId() {
		return boId;
	}
	public long getBoTypeId() {
		return boTypeId;
	}

	public String getContent() {
		return content;
	}
	public String getTitle() {
		return title;
	}
	public long getUserId() {
		return userId;
	}
	public void setBoId(long boId) {
		this.boId = boId;
	}
	public void setBoTypeId(long boTypeId) {
		this.boTypeId = boTypeId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}

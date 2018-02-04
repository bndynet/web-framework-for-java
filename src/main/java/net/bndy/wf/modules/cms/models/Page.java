/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.bndy.wf.lib._BaseEntity;
import org.springframework.data.domain.Pageable;

@Entity
@Table(name="cms_page")
public class Page extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private long channelId;
	private String title;
	@Column(columnDefinition="TEXT")
	private String content;
	@Transient
	private List<Attachment> attachments;
	@Transient
	private org.springframework.data.domain.Page<Comment> comments;
	
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
	public long getChannelId() {
		return channelId;
	}
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	public org.springframework.data.domain.Page<Comment> getComments() {
		return comments;
	}
	public void setComments(org.springframework.data.domain.Page<Comment> comments) {
		this.comments = comments;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
}

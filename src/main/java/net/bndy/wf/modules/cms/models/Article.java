/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.models;

import java.util.Set;

import javax.persistence.*;

import net.bndy.wf.lib._BaseEntity;
import net.bndy.wf.modules.core.models.File;

@Entity
@Table(name="cms_article")
public class Article extends _BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	@Column(columnDefinition = "BIGINT UNSIGNED")
	private long channelId;
	private String title;
	private String titleKey;
	
	@Column(columnDefinition="TEXT")
	private String content;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cms_article_file",
		joinColumns = @JoinColumn(name = "article_id"),
		inverseJoinColumns = @JoinColumn(name = "file_id"))
	private Set<File> attachments;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="channel_id", insertable = false, updatable = false)
	private Channel channel;
	
	public Set<File> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<File> attachments) {
		this.attachments = attachments;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleKey() {
		return titleKey;
	}
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
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
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}

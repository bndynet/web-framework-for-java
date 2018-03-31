/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services;

import java.io.File;
import java.util.List;

import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.BoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.modules.cms.models.Attachment;
import net.bndy.wf.modules.cms.models.Comment;
import net.bndy.wf.modules.cms.services.repositories.AttachmentRepository;
import net.bndy.wf.modules.cms.services.repositories.CommentRepository;

public abstract class _BaseService<T> extends net.bndy.wf.lib._BaseService<T> {

	@Autowired
	ApplicationConfig applicationConfig;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	AttachmentRepository attachmentRepository;

	public Page<Comment> getComments(long boId, Pageable pageable) {
		return this.commentRepository.findByBoId(boId, this.getBoTypeByGeneric().getValue(), pageable);
	}

	public Comment addComment(Comment comment, long pageId) {
		comment.setBoId(pageId);
		return this.commentRepository.saveAndFlush(comment);
	}

	public void transferComments(long sourceBoId, long targetBoId) {
		this.commentRepository.transfer(this.getBoTypeByGeneric().getValue(), sourceBoId, targetBoId);
	}

	public void deleteComment(long commentId) {
		this.commentRepository.delete(commentId);
	}

	public void deleteComments(long boId) {
		this.commentRepository.deleteByBo(boId, this.getBoTypeByGeneric().getValue());
	}

	public List<Attachment> getAttachments(long boId) {
		return this.attachmentRepository.findByBo(this.getBoTypeByGeneric().getValue(), boId);
	}

	public Attachment addAttachment(Attachment attachment) {
		attachment.setBoType(this.getBoTypeByGeneric());
		return this.attachmentRepository.saveAndFlush(attachment);
	}

	public void transferAttachment(long sourceBoId, long targetBoId) {
		this.attachmentRepository.transfer(this.getBoTypeByGeneric().getValue(), sourceBoId, targetBoId);
	}

	public void deleteAttachments(long boId) {
		for (Attachment attachment : this.attachmentRepository.findByBo(this.getBoTypeByGeneric().getValue(), boId)) {
			this.deleteAttachment(attachment.getId());
		}
	}

	public void deleteAttachment(long attachmentId) {
		Attachment entity = this.attachmentRepository.findOne(attachmentId);
		if (entity != null) {
			String filePath = this.applicationConfig.getUploadPath() + entity.getPath();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
		this.attachmentRepository.delete(entity);
	}

	protected BoType getBoTypeByGeneric() {
		BoType boType = null;
		if (this.domainClass.getName().equals(net.bndy.wf.modules.cms.models.Page.class.getName())) {
			boType = BoType.Page;
		} else if (this.domainClass.getName().equals(Article.class.getName())) {
			boType = BoType.Article;
		} else if (this.domainClass.getName().equals(Article.class.getName())) {
			boType = BoType.Resource;
		}
		return boType;
	}
}

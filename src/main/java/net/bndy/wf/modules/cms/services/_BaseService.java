/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services;

import java.io.File;

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
	CommentRepository commentRepo;
	@Autowired
	AttachmentRepository attachmentRepo;
	@Autowired
	ApplicationConfig applicationConfig;

	public Page<Comment> findByBoId(long pageId, Pageable pageable) {
		return this.commentRepo.findByBoId(pageId, pageable);
	}

	public Comment addComment(Comment comment, long pageId) {
		comment.setBoId(pageId);
		return this.commentRepo.saveAndFlush(comment);
	}

	public void deleteComment(long commentId) {
		this.commentRepo.delete(commentId);
	}

	public void deleteAllComments(long boId, long boTypeId) {
		this.commentRepo.deleteByBoIdAndBoTypeId(boId, boTypeId);
	}

	public Attachment addAttachment(Attachment attachment) {
		return this.attachmentRepo.saveAndFlush(attachment);
	}

	public void deleteAllAttachments(long boId, long boTypeId) {
		for (Attachment attachment : this.attachmentRepo.findByBo(boTypeId, boId)) {
			this.deleteAttachment(attachment.getId());
		}
	}

	public void deleteAttachment(long attachmentId) {
		Attachment entity = this.attachmentRepo.findOne(attachmentId);
		if (entity != null) {
			String filePath = this.applicationConfig.getUploadPath() + entity.getPath();
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
		this.attachmentRepo.delete(entity);
	}
}

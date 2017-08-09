package net.bndy.wf.modules.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.bndy.wf.modules.cms.models.Comment;
import net.bndy.wf.modules.cms.services.repositories.AttachmentRepository;
import net.bndy.wf.modules.cms.services.repositories.CommentRepository;

public abstract class _BaseService<T> extends net.bndy.wf.lib._BaseService<T> {
	
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	AttachmentRepository attachmentRepo;
	
	public Page<Comment> findByBoId(long pageId, Pageable pageable) {
		return this.commentRepo.findByBoId(pageId, pageable);
	}
	
	public Comment addComment(Comment comment, long pageId){
		comment.setBoId(pageId);
		return this.commentRepo.saveAndFlush(comment);
	}
	
	public void deleteComment(long commentId){
		this.commentRepo.delete(commentId);
	}
	
	public void deleteAllComments(long boId, long boTypeId) {
		this.commentRepo.deleteByBoIdAndBoTypeId(boId, boTypeId);
	}
}

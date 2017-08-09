package net.bndy.wf.modules.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.repositories.*;

@Service
public class PageService {
	
	@Autowired
	PageRepository pageRepo;
	@Autowired
	CommentRepository commentRepo;
	
	public Page get(long pageId) {
		return this.pageRepo.findOne(pageId);
	}
	
	public org.springframework.data.domain.Page<Comment> findByBoId(long pageId, Pageable pageable) {
		return this.commentRepo.findByBoId(pageId, pageable);
	}
	
	public Comment addComment(Comment comment, long pageId){
		comment.setBoId(pageId);
		return this.commentRepo.saveAndFlush(comment);
	}
	
	public void deleteComment(long commentId){
		this.commentRepo.delete(commentId);
	}
	
	public void deleteAllComments(long articleId) {
		this.commentRepo.deleteByBoId(articleId);
	}
}

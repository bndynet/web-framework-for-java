package net.bndy.wf.modules.cms.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.repositories.*;

@Service
@Transactional
public class ArticleService {

	@Autowired
	ArticleRepository articleRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	public Article get(long articleId){
		return this.articleRepo.findOne(articleId);
	}

	public Article save(Article entity) {
		return articleRepo.saveAndFlush(entity);
	}

	public void delete(long id) {
		this.articleRepo.delete(id);
	}

	public Page<Article> findAll(Pageable pageable) {
		return this.articleRepo.findAll(pageable);
	}

	public Page<Article> findByBoTypeId(long boTypeId, Pageable pageable) {
		return this.articleRepo.findByBoTypeId(boTypeId, pageable);
	}

	public Page<Article> findByKeywords(String keywords, Pageable pageable) {
		return this.articleRepo.findByKeywords(keywords, pageable);
	}

	public Page<Article> findByBoAndKeywords(long boTypeId, String keywords, Pageable pageable) {
		return this.articleRepo.findByBoAndKeywords(boTypeId, keywords, pageable);
	}

	public void deleteByBoTypeId(long boTypeId) {
		this.articleRepo.deleteByBoTypeId(boTypeId);
	}
	
	public Page<Comment> findByBoId(long articleId, Pageable pageable) {
		return this.commentRepo.findByBoId(articleId, pageable);
	}
	
	public Comment addComment(Comment comment, long articleId){
		comment.setBoId(articleId);
		return this.commentRepo.saveAndFlush(comment);
	}
	
	public void deleteComment(long commentId){
		this.commentRepo.delete(commentId);
	}
	
	public void deleteAllComments(long articleId) {
		this.commentRepo.deleteByBoId(articleId);
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
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
public class ArticleService extends _BaseService<Article> {

	@Autowired
	ArticleRepository articleRepo;
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	AttachmentRepository attachmentRepo;
	
	@Override
	public Article get(long article){
		Article result = this.articleRepo.findOne(article);
		result.setAttachments(this.attachmentRepo.findByBo(result.getBoTypeId(), result.getId()));
		return result;	
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
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services;

import javax.transaction.Transactional;

import net.bndy.lib.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.repositories.*;

import java.util.List;

@Service
@Transactional
public class ArticleService extends _BaseService<Article> {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public Article get(long article) {
        Article result = this.articleRepository.findOne(article);
        result.setAttachments(this.attachmentRepository.findByBo(BoType.Article.getValue(), result.getId()));
        return result;
    }

    @Override
    public Article save(Article entity) {
        boolean complexKey = true;
        String key = StringHelper.title2Url(entity.getTitle());
        Article existed = this.articleRepository.findByTitleKey(key);
        if (existed == null || existed.getId() == entity.getId()) {
            entity.setTitleKey(key);
            complexKey = false;
        }
        entity = super.save(entity);
        if (complexKey) {
            entity.setTitleKey(key + "-" + entity.getId());
            entity = super.save(entity);
        }
        return entity;
    }

    public Article getByTitleKey(String titleKey) {
        return this.articleRepository.findByTitleKey(titleKey);
    }

    public int countByChannelId(long channelId) {
        return this.articleRepository.countByChannelId(channelId);
    }

    public Page<Article> findByChannelId(long channelId, Pageable pageable) {
        return this.articleRepository.findByChannelId(channelId, pageable);
    }

    public Page<Article> findByKeywords(String keywords, Pageable pageable) {
        return this.articleRepository.findByKeywords(keywords, pageable);
    }

    public Page<Article> findByChannelIdAndKeywords(long channelId, String keywords, Pageable pageable) {
        return this.articleRepository.findByChannelIdAndKeywords(channelId, keywords, pageable);
    }

    public void deleteByChannelId(long channelId) {
        List<Article> articles = this.getAll();
        for (Article article : articles) {
            this.deleteAttachments(article.getId());
            this.deleteComments(article.getId());
        }
        this.articleRepository.deleteByChannelId(channelId);
    }

    public void transfer(long sourceChannelId, long targetChannelId) {
        this.articleRepository.transferChannel(sourceChannelId, targetChannelId);
    }
}

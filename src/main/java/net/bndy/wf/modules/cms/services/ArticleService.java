/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services;

import javax.transaction.Transactional;

import net.bndy.ftsi.NoKeyDefinedException;
import net.bndy.lib.CollectionHelper;
import net.bndy.lib.IOHelper;
import net.bndy.lib.StringHelper;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.cms.IndexModel;
import net.bndy.wf.modules.core.models.File;
import net.bndy.wf.modules.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticleService extends _BaseService<Article> {

    @Autowired
    private FileService fileService;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article get(long article) {
        Article result = this.articleRepository.findOne(article);
        return result;
    }

    @Override
    public Article save(Article entity) {
        // attachments checking
        if (entity.getId() != null) {
            Article origin = this.get(entity.getId());
            List<File> filesToDelete = new ArrayList<>();
            if (origin != null && origin.getAttachments() != null) {
                for (File f: origin.getAttachments()) {
                    if (entity.getAttachments() == null || !CollectionHelper.contains(entity.getAttachments(),(item) -> item.getId() == f.getId())) {
                        filesToDelete.add(f);
                    }
                }
            }

            for (File f: filesToDelete) {
                IOHelper.forceDelete(ApplicationContext.getFileFullPath(f.getPath()));
                this.fileService.delete(f.getId());
            }
        }

        boolean complexKey = true;
        String key = StringHelper.title2Url(entity.getTitle());
        Article existed = this.articleRepository.findByTitleKey(key);
        if (existed == null || existed.getId() == entity.getId()) {
            entity.setTitleKey(key);
            complexKey = false;
        }
        entity = super.save(entity);

        if (!CollectionHelper.isNullOrEmpty(entity.getAttachments())) {
            this.fileService.setRef(CollectionHelper.convert(entity.getAttachments(), (x -> x.getId())));
        }

        if (complexKey) {
            entity.setTitleKey(key + "-" + entity.getId());
            entity = super.save(entity);
        }

        try {
            ApplicationContext.getIndexService().updateIndex(new IndexModel(
                entity.getId(),
                entity.getTitle(),
                entity.getTitleKey(),
                StringHelper.stripHtml(entity.getContent()),
                BoType.Article.getName()
            ));
        } catch (NoKeyDefinedException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return entity;
    }

    @Override
    public boolean delete(long id) {
        try {
            ApplicationContext.getIndexService().deleteIndex(IndexModel.class, id);
        } catch (NoKeyDefinedException ex) {
            ex.printStackTrace();
        }
        return super.delete(id);
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

package net.bndy.wf.modules.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.Article;

@Service
public abstract class ArticleService implements ArticleRepository {
}

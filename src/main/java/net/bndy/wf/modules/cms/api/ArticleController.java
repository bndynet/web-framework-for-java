package net.bndy.wf.modules.cms.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.ArticleService;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public Article get(@PathVariable(name="id") long id) {
		return this.articleService.get(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<Article> get(
			@RequestParam(name = "bo", required = false) Integer boTypeId,
			@RequestParam(name = "keywords", required = false) String keywords,
			@PageableDefault(value = 10, sort = { "lastUpdate" }, direction = Sort.Direction.DESC) Pageable pageable) {
		if (boTypeId != null && boTypeId > 0 && keywords != null && keywords.length() > 0) {
			return this.articleService.findByBoAndKeywords(boTypeId, keywords, pageable);
		} else if (keywords != null && keywords.length() > 0) {
			return this.articleService.findByKeywords(keywords, pageable);
		} else if (boTypeId != null && boTypeId > 0) {
			return this.articleService.findByBoTypeId(boTypeId, pageable);
		}
		return this.articleService.findAll(pageable);
	}
}

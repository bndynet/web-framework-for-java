/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.api;

import net.bndy.wf.exceptions.DisabledFeatureException;
import net.bndy.wf.modules.core.models.File;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.ArticleService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "Article API")
@RestController
@RequestMapping({"/api/cms/articles", "/api/v1/cms/articles"})
public class ArticleController extends _BaseApi<Article> {

	@Autowired
	ArticleService articleService;
	
	@ApiOperation(value = "Search title and content by bo or keywords") 
	@RequestMapping(value="/search", method = RequestMethod.GET)
	public Page<Article> get(
			@RequestParam(name = "channel", required = false) Long channel,
			@RequestParam(name = "keywords", required = false) String keywords,
			@PageableDefault(value = 10, sort = { "lastUpdate" }, direction = Sort.Direction.DESC) Pageable pageable) {
		if (channel != null && channel > 0 && keywords != null && keywords.length() > 0) {
			return this.articleService.findByChannelIdAndKeywords(channel, keywords, pageable);
		} else if (keywords != null && keywords.length() > 0) {
			return this.articleService.findByKeywords(keywords, pageable);
		} else if (channel != null && channel > 0) {
			return this.articleService.findByChannelId(channel, pageable);
		}
		return this.articleService.findAll(pageable);
	}

	@Override
	public File upload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException, DisabledFeatureException {
		return super.upload(file, request);
	}
}

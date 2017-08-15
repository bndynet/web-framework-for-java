package net.bndy.wf.modules.cms.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.cms.models.Attachment;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.PageService;

@Api(value = "Page API")
@RestController
@RequestMapping("/api/v1/cms/pages")
public class PageController extends _BaseApi<Page> {
	
	@Autowired
	PageService pageService;
	
	@Override
	public String upload(MultipartFile file) throws IllegalStateException, IOException {
		String filePath = super.upload(file);
		
		Attachment attachment = new Attachment();
		// TODO: set properties
		this.pageService.addAttachment(attachment);
		
		return filePath;
	}
	
}

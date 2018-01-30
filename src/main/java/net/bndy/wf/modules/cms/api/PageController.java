/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bndy.wf.exceptions.ResourceIntegrityException;
import net.bndy.wf.modules.core.models.File;
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
@RequestMapping({"/api/cms/pages", "/api/v1/cms/pages"})
public class PageController extends _BaseApi<Page> {

	@Autowired
	PageService pageService;

	@Override
	public void delete(long id) throws ResourceIntegrityException {
		try {
			Page p = this.pageService.get(id);
			if (p != null) {
				this.pageService.deleteAllAttachments(p.getId(), p.getBoTypeId());
			}
			super.delete(id);
		} catch (Exception ex) {
			throw new ResourceIntegrityException("admin.modules.cms.page.errForDelete", ex);
		}
	}

	@Override
	public File upload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
		File fi = super.upload(file, request);

		Attachment attachment = new Attachment();
		attachment.setPath(fi.getPath());
		attachment.setExtensionName(fi.getExtName());
		attachment.setFileName(fi.getName());
		attachment.setFileType(fi.getType());
		attachment.setBoId(Long.parseLong(request.getParameter("boId")));
		attachment.setBoTypeId(Long.parseLong(request.getParameter("boTypeId")));
		this.pageService.addAttachment(attachment);

		return fi;
	}

}

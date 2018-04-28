/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;

import net.bndy.lib.IOHelper;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.core.services.FileService;
import net.bndy.wf.modules.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController extends _BaseController {

	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index() {
		if (!this.userService.hasUsers()) {
			return "redirect:/sso/login";
		}
		return "public/index";
	}

	@RequestMapping(value = "/files/{uuid:[\\w-]{36}}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public void get(@PathVariable(name = "uuid") String uuid, HttpServletResponse resp) throws IOException, NoResourceFoundException {
		net.bndy.wf.modules.core.models.File f = this.fileService.getByUuid(uuid);
		if (f != null) {
			String filePath = ApplicationContext.getFileFullPath(f.getPath());
			if (!IOHelper.isFileExisted(filePath)) {
				throw new NoResourceFoundException();
			}
			
			switch (f.getType()) {
				case TEXT:
					resp.setHeader("Content-Type", "text/plain");
				    break;
				case IMAGE:
				    resp.setHeader("Content-Type", "image/" + f.getExtName());
					break;
                default:
                    resp.addHeader("Content-Disposition",
						"attachment; filename=\"" + URLEncoder.encode(f.getFullname(), "UTF-8") + "\"");
                    break;
			}
			FileCopyUtils.copy(new FileInputStream(filePath), resp.getOutputStream());
			resp.flushBuffer();
		}
	}

	@RequestMapping(value = "/files/defaultAvatar", method = RequestMethod.GET, produces = {MediaType.IMAGE_PNG_VALUE})
	public void get(HttpServletResponse resp) throws IOException {
        String filePath = Paths.get(new ClassPathResource("/").getFile().getAbsolutePath(), this.applicationConfig.getDefaultUserAvatar()).toAbsolutePath().toString();
        FileCopyUtils.copy(new FileInputStream(filePath), resp.getOutputStream());
        resp.flushBuffer();
	}
}

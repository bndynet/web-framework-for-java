/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import java.io.*;
import java.nio.file.Paths;

import net.bndy.wf.modules.core.services.FileService;
import net.bndy.wf.modules.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
		return "/index";
	}

	@RequestMapping(value = "/files/{uuid:[\\w-]{36}}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	public void get(@PathVariable(name = "uuid") String uuid, HttpServletResponse resp) throws IOException {
		net.bndy.wf.modules.core.models.File f = this.fileService.getByUuid(uuid);
		if (f != null && this.applicationConfig.getUploadPath() != null && !applicationConfig.getUploadPath().isEmpty()) {
			String filePath = Paths.get(this.applicationConfig.getUploadPath(), f.getPath()).toAbsolutePath().toString();
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
	
	@RequestMapping("/test/upload")
	public String upload() {
		return "/upload";
	}
	
	@PostMapping("/test/upload/handler") 
	public String upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		String destFile = new File(".").getCanonicalPath() + File.separator + file.getOriginalFilename();
		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(new File(destFile));
		int read = 0;
		byte[] bytes = new byte[1024];
		while((read = in.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
		return file.getOriginalFilename();
	}
}

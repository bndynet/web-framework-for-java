/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController extends _BaseController {
	
	@RequestMapping("/")
	public String helloHtml(Map<String, Object> map) {
		List<String> lst = new ArrayList<String>();
		lst.add("/");
		lst.add("/v2/api-docs");
		lst.add("/swagger-ui.html");
		lst.add("/docs/api");

		map.put("time", new Date());
		map.put("urls", lst);
		
		map.put("who", this.getCurrentUser() != null ? this.getCurrentUser().getUsername() : "");

		return "/index";
	}
	
	@RequestMapping("/about")
	public String about() {
		return "/about";
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

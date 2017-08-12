package net.bndy.wf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThymeleafController {
	@RequestMapping("/")
	public String home(){
		return "/home";
	}
	
	@RequestMapping("/hello")
	public String helloHtml(Map<String, Object> map) {
		List<String> lst = new ArrayList<String>();
		lst.add("/");
		lst.add("/v2/api-docs");
		lst.add("/swagger-ui.html");
		lst.add("/docs/api");

		map.put("who", "Bing Z");
		map.put("time", new Date());
		map.put("urls", lst);

		return "/hello";
	}
}

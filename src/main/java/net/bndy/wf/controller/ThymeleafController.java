package net.bndy.wf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	@RequestMapping("/hello")
	public String helloHtml(Map<String, Object> map) {
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			lst.add(i);
		}

		map.put("who", "Bing Z");
		map.put("time", new Date());
		map.put("ids", lst);

		return "/hello";
	}
}

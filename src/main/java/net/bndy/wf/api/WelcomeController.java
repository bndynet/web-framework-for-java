package net.bndy.wf.api;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WelcomeController {
	
	//e.g. /api/index?name=Bndy
	@RequestMapping("/index")
    public String index(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format("Hello, %s!", name);
    }
	
	//e.g. /api/get/1
	@RequestMapping("/get/{id}")
	public Object get(@PathVariable(name="id") int id){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", id);
		result.put("name", "Bndy");
		result.put("dt", new Date());
		result.put("dtString", new Date().toString());
		return result;
	}
}

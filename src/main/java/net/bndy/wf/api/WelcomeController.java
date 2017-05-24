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
	
	//e.g. /api/welcome?name=Bndy
	@RequestMapping("/welcome")
    public String index(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format("Hello, %s!", name);
    }
}

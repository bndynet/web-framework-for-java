package net.bndy.wf.modules.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.app.services.AppMenuRepository;

@RestController
@RequestMapping("/api/appmenu")
public class AppMenuController {
	@Autowired
	private AppMenuRepository appMenuRepo;

	@RequestMapping(value="", method = RequestMethod.GET)
	public List<Menu> get() {
		Menu m = new Menu();
		m.setName("Name");
		this.appMenuRepo.saveAndFlush(m);
		return this.appMenuRepo.findAll();
	}
}

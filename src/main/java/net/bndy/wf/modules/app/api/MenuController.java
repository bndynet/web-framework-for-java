package net.bndy.wf.modules.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.app.services.MenuService;

@RestController
@RequestMapping("/api/v1/app/menus")
public class MenuController extends _BaseApi<Menu> {
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public List<Menu> get(@RequestParam(name = "all", required = false, defaultValue = "false") boolean all) {
		if (all) {
			return this.menuService.getMenus();
		} else {
			return this.menuService.getUserMenus();
		}
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.modules.core.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.MenuService;

@Api(value = "Menu API")
@RestController
@RequestMapping({"/api/core/menus", "/api/v1/core/menus"})
public class MenuController extends _BaseApi<Menu> {

	@Autowired
	private MenuService menuService;
	@Autowired
	private ApplicationConfig applicationConfig;

	@ApiOperation(value = "Get menus with children")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public List<Menu> get(@RequestParam(name = "all", required = false, defaultValue = "false") boolean all) {

		boolean isAdmin = ApplicationContext.isUserInRole(this.applicationConfig.getAdminRole()[0]);

	    List<Menu> result;
		if (all && isAdmin) {
			result = this.menuService.getAllMenuList();
		} else {
			List<Menu> tmpMenus = this.menuService.getVisibleMenuList();
			if (isAdmin) {
				result = tmpMenus;
			} else {
				result = new ArrayList<>();
				for (Role r: ApplicationContext.getCurrentUser().getRoles()) {
					if (r.getMenuIds() != null && r.getMenuIds() != "") {
						String menuIds = "|" + r.getMenuIds().replace(",", "||") + "|";
						for (Menu m : tmpMenus) {
							if (menuIds.indexOf("|" + m.getId().toString() + "|") > -1) {
								if (!result.contains(m)) {
									result.add(m);
								}
							}
						}
					}
				}
			}
		}

		result = menuService.convertList2Tree(result);
		// Append menu management entry for Admin user
        if (isAdmin) {
            result.add(this.menuService.getMenuManagementEntry());
        }
		return result;
	}

	@ApiOperation(value = "Toggle menu visible")
	@RequestMapping(value = "/{id}/toggleVisible", method = RequestMethod.PUT)
	public void toggleVisible(@PathVariable(name = "id") long id) {
		this.menuService.toggleVisible(id);
	}


	@ApiOperation(value = "Gets menu templates")
	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public List<Menu> getTemplates() throws IOException {
		return this.menuService.getTemplates();
	}
}

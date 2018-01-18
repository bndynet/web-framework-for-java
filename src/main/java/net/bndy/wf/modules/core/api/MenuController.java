/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.api;

import java.io.IOException;
import java.util.List;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.config.ApplicationUserRole;
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

	@ApiOperation(value = "Get menus with children")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public List<Menu> get(@RequestParam(name = "all", required = false, defaultValue = "false") boolean all) throws IOException {
	    List<Menu> result;
		if (all) {
			result = this.menuService.getMenus();
		} else {
			result = this.menuService.getUserMenus();
		}

		// Append menu management entry for Admin user
        if (ApplicationContext.isUserInRole(ApplicationUserRole.Admin)) {
            result.add(this.menuService.getMenuManagementEntry());
        }
		return  result;
	}

	@ApiOperation(value = "Toggle menu visible")
	@RequestMapping(value = "/{id}/toggleVisible", method = RequestMethod.PUT)
	public void toggleVisible(@PathVariable(name = "id") long id) {
		this.menuService.toggleVisible(id);
	}
}

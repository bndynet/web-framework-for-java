/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.app.services.MenuService;

@Api(value = "Menu API")
@RestController
@RequestMapping("/api/v1/app/menus")
public class MenuController extends _BaseApi<Menu> {
	@Autowired
	private MenuService menuService;

	@ApiOperation(value = "Get menus with children")
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public List<Menu> get(@RequestParam(name = "all", required = false, defaultValue = "false") boolean all) {
		if (all) {
			return this.menuService.getMenus();
		} else {
			return this.menuService.getUserMenus();
		}
	}
}

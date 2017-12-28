package net.bndy.wf.test.modules.app;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.lib.AppBoType;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.MenuService;
import net.bndy.wf.test._Test;

public class MenuServiceTest extends _Test {
	
	@Autowired
	MenuService menuService;

	@Test
	public void crud() throws Exception {
		Menu menu = new Menu();
		menu.setName(this.getTimestampString());
		menu.setBoType(AppBoType.CMS_PAGE);
		menu = this.menuService.save(menu);
		
		Assert.assertTrue(menu.getId() > 0);
		
		menu.setName("changed");
		menu = this.menuService.save(menu);
		Assert.assertTrue(menu.getName() == "changed");
		
		long id = menu.getId();
		this.menuService.delete(id);
		menu = this.menuService.getMenu(id);
		Assert.assertTrue(menu == null);
	}	
}

package net.bndy.wf.test;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.modules.app.AppBoType;
import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.app.services.MenuService;

public class InitDataTest extends _Test {

	@Autowired
	MenuService menuService;

	@Test
	public void initSeed() {
		initMenuChildren(null, 10);
	}

	private void initMenuChildren(Menu pmenu, int count) {
		for (int i = 1; i < count; i++) {
			Menu menu = new Menu();

			if (pmenu == null) {
				menu.setName("Menu " + i);
			} else {
				menu.setName(pmenu.getName() + "  - " + i);
				menu.setParentId(pmenu.getId());
			}

			switch (i % 5) {
			case 2:
				break;

			case 3:
				menu.setBoType(AppBoType.CMS_PAGE);
				break;

			case 4:
				menu.setBoType(AppBoType.CMS_ARTICLE);
				break;

			default:
				menu.setBoType(AppBoType.CMS_PAGE);
				break;
			}

			menu = this.menuService.save(menu);

			if (menu.getBoType() == null) {
				this.initMenuChildren(menu, count - 4);
			}
		}
	}
}

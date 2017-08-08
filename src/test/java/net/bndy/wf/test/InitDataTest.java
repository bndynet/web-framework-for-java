package net.bndy.wf.test;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.modules.app.AppBoType;
import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.app.services.MenuService;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleRepository;
import net.bndy.wf.modules.cms.services.PageRepository;

public class InitDataTest extends _Test {

	@Autowired
	ArticleRepository articleRepo;
	@Autowired
	PageRepository pageRepo;
	@Autowired
	MenuService menuService;

	@Test
	public void initSeed() {
		initMenuChildren(null, 10);
		initMenuData();
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

	private void initMenuData() {
		List<Menu> menus = this.menuService.getList();
		for (Menu m : menus) {
			AppBoType boType = m.getBoType();
			if (boType != null) {
				switch (m.getBoType()) {
				case CMS_PAGE:
					Page p = pageRepo.getByBoTypeId(m.getBoTypeId());
					p.setContent("Content for " + m.getName());
					p = pageRepo.saveAndFlush(p);
					break;

				case CMS_ARTICLE:
					for (int i = 0; i < 15; i++) {
						Article article = new Article();
						article.setBoTypeId(m.getBoTypeId());
						article.setTitle(m.getName() + " - Title " + i);
						article.setContent("Content " + i);
						articleRepo.saveAndFlush(article);
					}
					break;

				default:
				}
			}
		}
	}
}

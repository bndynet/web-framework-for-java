package net.bndy.wf.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.lib.AppBoType;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.MenuService;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.Comment;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.repositories.PageRepository;

public class InitDataTest extends _Test {

	@Autowired
	MenuService menuService;
	@Autowired
	ArticleService articleService;
	@Autowired
	PageRepository pageRepo;

	@Test
	public void initSeed() {
		initMenuChildren(null, 10);
		initMenuData();
		Assert.assertTrue(this.menuService.getList().size() > 0);
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
				Comment comment = new Comment();
				switch (m.getBoType()) {
				case CMS_PAGE:
					Page p = this.pageRepo.getByBoTypeId(m.getBoTypeId());
					p.setContent("Content for " + m.getName());
					p = this.pageRepo.saveAndFlush(p);
					comment.setBoId(p.getId());
					comment.setTitle("Comment Title for " + p.getTitle());
					comment.setContent("Comment Content for " + p.getTitle());
					this.articleService.addComment(comment, p.getId());
					break;

				case CMS_ARTICLE:
					for (int i = 0; i < 15; i++) {
						Article article = new Article();
						article.setBoTypeId(m.getBoTypeId());
						article.setTitle(m.getName() + " - Title " + i);
						article.setContent("Content " + i);
						article = this.articleService.save(article);
						comment.setBoId(article.getId());
						comment.setTitle("Comment Title for " + article.getTitle());
						comment.setContent("Comment Content for " + article.getTitle());
						this.articleService.addComment(comment, article.getId());
					}
					break;

				default:
				}
			}
		}
	}
}

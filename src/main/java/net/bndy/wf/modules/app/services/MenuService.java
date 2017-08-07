package net.bndy.wf.modules.app.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleRepository;
import net.bndy.wf.modules.cms.services.PageRepository;

@Service
@Transactional
public class MenuService {

	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private PageRepository pageRepo;
	@Autowired
	private ArticleRepository articleRepo;

	public List<Menu> getMenus() {
		List<Menu> menus = this.menuRepo.findAll();
		List<Menu> rootMenus = new ArrayList<Menu>();
		for (Menu m : menus) {
			if (m.getParentId() <= 0) {
				m.setChildren(this.getChildrenMenus(m, menus));
				rootMenus.add(m);
			}
		}
		return rootMenus;
	}

	public Menu getMenu(long id) {
		return this.menuRepo.findOne(id);
	}

	public List<Menu> getUserMenus() {
		List<Menu> menus = this.menuRepo.getUserMenus();
		List<Menu> rootMenus = new ArrayList<Menu>();
		for (Menu m : menus) {
			if (m.getParentId() <= 0) {
				m.setChildren(this.getChildrenMenus(m, menus));
				rootMenus.add(m);
			}
		}
		return rootMenus;
	}

	private List<Menu> getChildrenMenus(Menu menu, List<Menu> menus) {
		List<Menu> result = new ArrayList<Menu>();
		for (Menu m : menus) {
			if (m.getParentId() == menu.getId()) {
				menu.getChildren().add(m);
			}
		}
		return result;
	}

	public Menu save(Menu menu) {
		Menu result = this.menuRepo.saveAndFlush(menu);
		
		if(result.getBoTypeId() == 0) {
			result.setBoTypeId(result.getId());
			result = this.menuRepo.saveAndFlush(menu);
		}
		
		if (menu.getBoType() != null) {
			switch (menu.getBoType()) {
			case CMS_PAGE:
				Page page = this.pageRepo.getByBoTypeId(menu.getBoTypeId());
				if (page == null) {
					page = new Page();
					page.setTitle(menu.getName());
					page.setBoTypeId(menu.getBoTypeId());
					page = this.pageRepo.saveAndFlush(page);
				}
				break;
				
			case CMS_ARTICLE:
				break;

			default:
				break;
			}
		}
		return result;
	}

	public void toggleVisible(long id) {
		Menu m = this.menuRepo.findOne(id);
		if (m != null) {
			m.setHidden(!m.isHidden());
			this.menuRepo.saveAndFlush(m);
		}
	}

	public void delete(long id) throws Exception {
		List<Menu> children = this.menuRepo.getChildren(id);
		if (children.size() == 0) {
			Menu menu = this.menuRepo.findOne(id);
			if (menu != null && menu.getBoType() != null) {
				switch (menu.getBoType()) {
				case CMS_PAGE:
					this.pageRepo.deleteByBoTypeId(menu.getBoTypeId());
					break;

				case CMS_ARTICLE:
					this.articleRepo.deleteByBoTypeId(menu.getBoTypeId());
					break;
				default:
					break;
				}
			}
			this.menuRepo.delete(id);
		}
		else {
			throw new Exception("The current menu has children.");
		}
	}
}

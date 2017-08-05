package net.bndy.wf.modules.app.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.PageRepository;

@Service
@Transactional
public class MenuService {

	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private PageRepository pageRepo;

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
		List<Menu> menus = this.menuRepo.GetUserMenus();
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
		if (menu.getBoType() != null) {
			switch (menu.getBoType()) {
			case CMS_PAGE:
				Page page = null;
				if (menu.getBoTypeId() > 0) {
					page = this.pageRepo.getByBoTypeId(menu.getBoTypeId());
				}
				if (page == null) {
					page = new Page();
					page.setTitle(menu.getName());
				}
				menu.setBoTypeId(this.pageRepo.saveAndFlush(page).getId());
				break;
			default:
				break;
			}
		}
		return this.menuRepo.saveAndFlush(menu);
	}

	public void toggleVisible(long id) {
		Menu m = this.menuRepo.findOne(id);
		if (m != null) {
			m.setHidden(!m.isHidden());
			this.menuRepo.saveAndFlush(m);
		}
	}

	public void delete(long id) {
		Menu menu = this.menuRepo.findOne(id);
		if (menu != null && menu.getBoType() != null) {
			switch (menu.getBoType()) {
			case CMS_PAGE:
				this.pageRepo.deleteByBoTypeId(menu.getBoTypeId());
				break;

			case CMS_ARTICLE:
				break;
			default:
				break;
			}
		}
		this.menuRepo.delete(id);
	}
}

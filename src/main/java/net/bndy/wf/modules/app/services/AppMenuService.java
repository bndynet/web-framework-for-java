package net.bndy.wf.modules.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.app.models.Menu;
import net.bndy.wf.modules.cms.services.PageRepository;

@Service
@Transactional
public class AppMenuService {
	
	@Autowired
	private AppMenuRepository appMenuRepo;
	@Autowired
	private PageRepository pageRepo;
	
	public List<Menu> getAll() {
		return appMenuRepo.findAll();
	}
	
	public void delete(long id){
		Menu menu = this.appMenuRepo.findOne(id);
		if(menu!=null){
			switch(menu.getBoType()) {
			case CMS_PAGE:
//				this.pageRepo.deleteByBoTypeId(menu.getBoTypeId());
				break;
				
			case CMS_ARTICLE:
				break;
			default:
				break;
			}
		}
	}
}

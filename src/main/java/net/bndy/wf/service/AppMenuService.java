package net.bndy.wf.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.domain.*;
import net.bndy.wf.repository.*;

@Service
@Transactional
public class AppMenuService {
	
	@Autowired
	private AppMenuRepository appMenuRepo;
	@Autowired
	private PageRepository pageRepo;
	@Autowired
	private ArticleRepository articleRepo;
	
	public List<AppMenu> getAll() {
		return appMenuRepo.findAll();
	}
	
	public void delete(long id){
		AppMenu menu = this.appMenuRepo.findOne(id);
		if(menu!=null){
			switch(menu.getBoType()) {
			case PAGE:
				this.pageRepo.deleteByBoTypeId(menu.getBoTypeId());
				break;
				
			case ARTICLE:
				break;
			}
		}
	}
}

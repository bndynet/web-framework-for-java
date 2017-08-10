package net.bndy.wf.modules.cms.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.modules.cms.models.*;
import net.bndy.wf.modules.cms.services.repositories.*;

@Service
@Transactional
public class PageService extends _BaseService<Page> {
	
	@Autowired
	PageRepository pageRepo;
	
	@Override
	public Page get(long pageId) {
		Page result = this.pageRepo.findOne(pageId);
		result.setAttachments(this.attachmentRepo.findByBo(result.getBoTypeId(), result.getId()));
		return result;	
	}
}

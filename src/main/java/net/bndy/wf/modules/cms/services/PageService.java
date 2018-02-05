/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
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
    PageRepository pageRepository;

    public Page getByChannelId(long channelId) {
        Page result = this.pageRepository.findByChannelId(channelId);
        if (result != null) {
            result.setAttachments(this.getAttachments(result.getId()));
        }
        return result;
    }

    public int countByChannelId(long channelId) {
        return this.pageRepository.countByChannelId(channelId);
    }

    public void deleteByChannelId(long channelId) {
        Page page = this.pageRepository.findByChannelId(channelId);
        if (page != null) {
            this.delete(page.getId());
        }
    }

    @Override
    public boolean delete(long id) {
        Page p = this.pageRepository.findOne(id);
        if (p != null) {
            this.deleteComments(p.getId());
            this.deleteAttachments(p.getId());
        }
        return super.delete(id);
    }

    public void transfer(long sourceChannelId, long targetChannelId) {
        Page sourcePage = this.getByChannelId(sourceChannelId);
        Page targetPage = this.getByChannelId(targetChannelId);
        if (sourcePage != null) {
            if (targetPage == null) {
                targetPage = new Page();
                targetPage.setChannelId(targetChannelId);
                targetPage.setContent(sourcePage.getContent());
            } else {
                targetPage.setContent(targetPage.getContent() + sourcePage.getContent());
            }
            targetPage = this.pageRepository.saveAndFlush(targetPage);

            this.transferAttachment(sourcePage.getId(), targetPage.getId());
            this.transferComments(sourcePage.getId(), targetPage.getId());

            this.pageRepository.transferChannel(sourcePage.getId(), targetPage.getId());
            this.pageRepository.delete(sourcePage.getId());
        }
    }
}

package net.bndy.wf.modules.cms.services;

import net.bndy.wf.modules.cms.models.Resource;
import net.bndy.wf.modules.cms.services.repositories.ResourceRepository;
import net.bndy.wf.modules.core.models.File;
import net.bndy.wf.modules.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ResourceService extends _BaseService<Resource> {

    @Autowired
    private FileService fileService;
    @Autowired
    private ResourceRepository resourceRepository;

    public List<File> getFilesByChannelId(long channelId) {
        List<Long> fileIds = this.resourceRepository.findByChannelId(channelId);
        if (fileIds == null || fileIds.isEmpty()) {
            return new ArrayList<>();
        }

        return this.fileService.getFilesByIds(fileIds);
    }

    public void deleteFile(long fileId) {
        Resource resource = this.resourceRepository.findByFileId(fileId);
        if (resource !=null) {
            this.fileService.delete(resource.getFileId());
            this.delete(resource.getId());
        }
    }
}

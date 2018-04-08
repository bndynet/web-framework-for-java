package net.bndy.wf.modules.cms.services;

import net.bndy.lib.CollectionHelper;
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
        List<Resource> resources = this.resourceRepository.findByChannelId(channelId);
        List<Long> fileIds = CollectionHelper.convert(resources, (resource -> resource.getFileId()));
        if (fileIds == null) {
            return new ArrayList<>();
        }

        return this.fileService.getFilesByIds(fileIds);
    }

    public int countByChannelId(long channelId) {
        return this.resourceRepository.countByChannelId(channelId);
    }

    public void deleteFile(long fileId) {
        Resource resource = this.resourceRepository.findByFileId(fileId);
        if (resource != null) {
            this.fileService.delete(resource.getFileId());
            this.delete(resource.getId());
        }
    }

    public void deleteByChannelId(long channelId) {
        List<Resource> resources = this.resourceRepository.findByChannelId(channelId);
        for (Resource resource : resources) {
            this.deleteFile(resource.getFileId());
        }
    }

    public void transfer(long sourceChannelId, long targetChannelId) {
        this.resourceRepository.transferChannel(sourceChannelId, targetChannelId);
    }
}

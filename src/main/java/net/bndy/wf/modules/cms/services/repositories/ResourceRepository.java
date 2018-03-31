package net.bndy.wf.modules.cms.services.repositories;

import net.bndy.wf.modules.cms.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    Resource findByFileId(long fileId);
    List<Long> findByChannelId(long channelId);
}

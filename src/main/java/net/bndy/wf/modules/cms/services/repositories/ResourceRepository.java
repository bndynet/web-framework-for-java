package net.bndy.wf.modules.cms.services.repositories;

import net.bndy.wf.modules.cms.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    int countByChannelId(long channelId);
    Resource findByFileId(long fileId);
    List<Resource> findByChannelId(long channelId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Resource t SET t.channelId = ?2 WHERE t.channelId = ?1")
    void transferChannel(long sourceChannelId, long targetChannelId);
}

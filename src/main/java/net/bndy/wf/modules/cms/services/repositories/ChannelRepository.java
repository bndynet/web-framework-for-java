package net.bndy.wf.modules.cms.services.repositories;

import net.bndy.wf.modules.cms.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE cms_channel SET is_visible=1 WHERE id IN (:ids)", nativeQuery = true)
    void openVisible(@Param("ids") List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cms_channel SET is_visible=0 WHERE path LIKE :path%", nativeQuery = true)
    void hideAllChildren(@Param("path") String path);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cms_channel WHERE path LIKE :path%", nativeQuery = true)
    void deleteByPath(@Param("path") String path);

    @Query(value = "SELECT COUNT(*) FROM cms_channel WHERE path LIKE :path%", nativeQuery = true)
    int countByPath(@Param("path") String path);
}

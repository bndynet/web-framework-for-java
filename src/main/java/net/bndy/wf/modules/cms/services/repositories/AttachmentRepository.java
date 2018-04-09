/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.modules.cms.models.Attachment;
import org.springframework.transaction.annotation.Transactional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
	@Query(value="SELECT * FROM cms_attachment "
			+ "WHERE bo_type = :boType AND bo_id = :boId "
			+ "ORDER BY last_update DESC", nativeQuery = true)
	List<Attachment> findByBo(@Param(value="boType") int boType, @Param(value="boId") long boId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cms_attachment WHERE bo_type = ?1 AND bo_id = ?2", nativeQuery = true)
	void deleteByBo(int boType, long boId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE cms_attachment SET bo_id = ?3 WHERE bo_id = ?2 AND bo_type = ?1", nativeQuery = true)
	void transfer(int boType, long sourceBoId, long targetBoId);
}

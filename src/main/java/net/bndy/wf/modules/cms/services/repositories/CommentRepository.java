/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.modules.cms.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cms_comment WHERE bo_id = :boId AND bo_type = :boType", nativeQuery = true)
	void deleteByBo(@Param(value = "boId") long boId, @Param(value = "boType") int boType);
	
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.boId = :boId AND t.boType = :boType "
			+ "ORDER BY t.lastUpdate DESC ")
	Page<Comment> findByBoId(@Param(value="boId") long boId, @Param(value = "boType") int boType, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "UPDATE cms_comment SET bo_id = ?3 WHERE bo_id = ?2 AND bo_type = ?1", nativeQuery = true)
	void transfer(int boType, long sourceBoId, long targetBoId);
}

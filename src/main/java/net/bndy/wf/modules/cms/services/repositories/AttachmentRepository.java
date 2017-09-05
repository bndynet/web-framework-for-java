/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.modules.cms.models.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.boTypeId = :boTypeId AND t.boId = :boId "
			+ "ORDER BY t.lastUpdate DESC")
	List<Attachment> findByBo(@Param(value="boTypeId") long boTypeId, @Param(value="boId") long boId);
}

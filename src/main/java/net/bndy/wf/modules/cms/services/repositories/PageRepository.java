/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.modules.cms.models.*;

public interface PageRepository extends JpaRepository<Page, Long> {

	int countByChannelId(long channelId);
	Page findByTitle(String title);

	@Query(value="SELECT t FROM Page t WHERE t.channelId = :channelId")
	Page findByChannelId(@Param(value="channelId") long channelId);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM Page t WHERE t.channelId = :channelId")
	void deleteByChannelId(@Param(value="channelId") long channelId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Page t SET t.channelId = ?2 WHERE t.channelId = ?1")
	void transferChannel(long sourceChannelId, long targetChannelId);
}

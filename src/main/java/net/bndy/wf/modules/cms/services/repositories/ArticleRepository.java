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

import net.bndy.wf.modules.cms.models.*;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    int countByChannelId(long channelId);
    Article findByTitleKey(String titleKey);

    List<Article> findByChannelId(long channelId);

    @Query(value = "SELECT t FROM #{#entityName} t "
        + "WHERE t.channelId= :channelId "
        + "ORDER BY t.lastUpdate DESC")
    Page<Article> findByChannelId(@Param(value = "channelId") long channelId, Pageable pageable);

    @Query(value = "SELECT t FROM #{#entityName} t "
        + "WHERE t.title LIKE %:keywords% OR t.content LIKE %:keywords% "
        + "ORDER BY t.lastUpdate DESC")
    Page<Article> findByKeywords(@Param(value = "keywords") String keywords, Pageable pageable);

    @Query(value = "SELECT t FROM #{#entityName} t "
        + "WHERE t.channelId = :channelId AND (t.title LIKE %:keywords% OR t.content LIKE %:keywords%) "
        + "ORDER BY t.lastUpdate DESC")
    Page<Article> findByChannelIdAndKeywords(
        @Param(value = "channelId") long channelId,
        @Param(value = "keywords") String keywords,
        Pageable pageable);

    @Modifying
    @Transactional
    void deleteByChannelId(long channelId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Article t SET t.channelId = ?2 WHERE t.channelId = ?1")
    void transferChannel(long sourceChannelId, long targetChannelId);
}

package net.bndy.wf.modules.cms.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.modules.cms.models.*;

public interface ArticleRepository extends JpaRepository<Article, Long> {

//	@Query(value="SELECT * FROM cms_article WHERE bo_type_id = :boTypeId ORDER BY last_update DESC", 
//			countQuery="SELECT COUNT(*) FROM cms_article WHERE bo_type_id = :boTypeId",
//			nativeQuery=true)
//	Page<Article> getByBoTypeId(@Param(value="boTypeId") long boTypeId, Pageable pageable);
//	
//	@Query(value="SELECT * FROM cms_article WHERE title LIKE %:keywords% OR content LIKE %:keywords% ORDER BY last_update DESC", 
//			countQuery="SELECT COUNT(*) FROM cms_article WHERE title LIKE %:keywords% OR content LIKE %:keywords%",
//			nativeQuery=true)
//	Page<Article> getByKeywords(@Param(value="keywords") String keywords, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM cms_article WHERE bo_type_id = :boTypeId", nativeQuery=true)
	void deleteByBoTypeId(@Param(value="boTypeId") long boTypeId);
}
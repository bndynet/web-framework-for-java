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
	
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.boTypeId = :boTypeId "
			+ "ORDER BY t.lastUpdate DESC")
	Page<Article> findByBoTypeId(@Param(value="boTypeId") long boTypeId, Pageable pageable);
	
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.title LIKE %:keywords% OR t.content LIKE %:keywords% "
			+ "ORDER BY t.lastUpdate DESC")
	Page<Article> findByKeywords(@Param(value="keywords") String keywords, Pageable pageable);
	
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.boTypeId = :boTypeId AND (t.title LIKE %:keywords% OR t.content LIKE %:keywords%) "
			+ "ORDER BY t.lastUpdate DESC")
	Page<Article> findByBoAndKeywords(
			@Param(value="boTypeId") long boTypeId, 
			@Param(value="keywords") String keywords, 
			Pageable pageable);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM #{#entityName} t WHERE t.boTypeId = :boTypeId")
	void deleteByBoTypeId(@Param(value="boTypeId") long boTypeId);
}
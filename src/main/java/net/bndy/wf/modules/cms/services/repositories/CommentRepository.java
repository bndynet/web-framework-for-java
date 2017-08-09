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
	void deleteByBoId(long boId);
	
	@Query(value="SELECT t FROM #{#entityName} t "
			+ "WHERE t.boId = :boId "
			+ "ORDER BY t.lastUpdate DESC ")
	Page<Comment> findByBoId(@Param(value="boId") long boId, Pageable pageable);
}

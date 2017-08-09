package net.bndy.wf.modules.cms.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.modules.cms.models.*;

public interface PageRepository extends JpaRepository<Page, Long> {

	@Query(value="SELECT * FROM cms_page WHERE bo_type_id = :boTypeId", nativeQuery=true)
	Page getByBoTypeId(@Param(value="boTypeId") long boTypeId);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM cms_page WHERE bo_type_id = :boTypeId", nativeQuery=true)
	void deleteByBoTypeId(@Param(value="boTypeId") long boTypeId);
}

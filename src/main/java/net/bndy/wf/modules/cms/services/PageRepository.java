package net.bndy.wf.modules.cms.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.bndy.wf.modules.cms.models.*;

public interface PageRepository extends JpaRepository<Page, Long> {

//	@Query(value="delete from page where boTypeId=:boTypeId")
//	void deleteByBoTypeId(long boTypeId);
}

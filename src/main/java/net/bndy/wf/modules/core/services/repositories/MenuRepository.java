/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.modules.core.models.Menu;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	Menu findByName(String name);
	
	@Query(value="SELECT t.* FROM core_menu t WHERE t.is_visible = 1", nativeQuery=true)
	List<Menu> getVisibleMenus();
	
	@Query(value="SELECT * FROM core_menu t WHERE t.parent_id = :id", nativeQuery=true)
	List<Menu> getChildren(@Param("id") long id);

	@Query(value="SELECT * FROM core_menu t WHERE t.parents LIKE :parents%", nativeQuery=true)
	List<Menu> getAllChildren(@Param("parents") String parents);

	@Modifying
	@Transactional
	@Query(value="UPDATE core_menu SET is_visible=(SELECT t.is_visible FROM (SELECT is_visible FROM core_menu WHERE id = :id) as t) WHERE id IN (:parentIds)", nativeQuery=true)
	void syncParentsVisible(@Param("id") long id, @Param("parentIds") List<Long> parentIds);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM core_menu WHERE parents LIKE :parents%", nativeQuery = true)
	void deleteByParents(@Param("parents") String parents);
}

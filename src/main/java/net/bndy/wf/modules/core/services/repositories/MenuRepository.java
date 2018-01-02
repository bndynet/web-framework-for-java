/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.bndy.wf.modules.core.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	@Query(value="SELECT t.* FROM core_menu t WHERE t.is_hidden != 1", nativeQuery=true)
	List<Menu> getUserMenus();
	
	@Query(value="SELECT * FROM core_menu t WHERE t.parent_id = :id", nativeQuery=true)
	List<Menu> getChildren(@Param("id") long id);
}

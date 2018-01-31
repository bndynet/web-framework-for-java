/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import net.bndy.wf.modules.core.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Long>  {
	Role findByName(String name);

	@Modifying
	@Transactional
	@Query(value = "UPDATE core_role SET menu_ids = :menuIds WHERE id = :roleId", nativeQuery = true)
    void updateMenus(@Param("roleId") long roleId, @Param("menuIds") String menuIds);

	@Query(value = "SELECT COUNT(1) FROM core_user_role WHERE role_id = :roleId", nativeQuery = true)
	int countByRoleId(@Param("roleId") long roleId);
}

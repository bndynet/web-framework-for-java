/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.app.models.*;

public interface RoleRepository extends JpaRepository<Role, Long>  {
	Role findByName(String name);
}

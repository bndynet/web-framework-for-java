/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.core.models.ClientUser;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {

	ClientUser findByUserIdAndClientId(long userId, String clientId);
	ClientUser findByAuthorizationCode(String authorizationCode);
	List<ClientUser> findByUserId(long userId);
	void deleteByUserIdAndClientId(long userId, String clientId);
	void deleteByClientId(String clientId);
}

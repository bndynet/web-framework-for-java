/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import net.bndy.wf.modules.core.models.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {

    OauthClientDetails findByClientId(String clientId);

    void deleteByAppClientId(Long appClientId);
}

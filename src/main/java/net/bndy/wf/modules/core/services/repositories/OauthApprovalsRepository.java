/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services.repositories;

import net.bndy.wf.modules.core.models.OauthApprovals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OauthApprovalsRepository extends JpaRepository<OauthApprovals, Long> {

    OauthApprovals findByUserIdAndClientId(String userId, String clientId);

    Collection<OauthApprovals> findByUserId(String userId);
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.oauth.repositories;

import net.bndy.wf.modules.oauth.models.OauthApprovals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OauthApprovalsRepository extends JpaRepository<OauthApprovals, Long> {

    OauthApprovals findByUserIdAndClientId(String userId, String clientId);

    Collection<OauthApprovals> findByUserId(String userId);
}

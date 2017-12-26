/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services;

import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.core.models.OauthClientDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OauthClientDetailsService extends _BaseService<OauthClientDetails> {

}

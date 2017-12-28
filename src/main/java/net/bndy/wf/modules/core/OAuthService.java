/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core;

import net.bndy.wf.modules.core.models.User;
import net.bndy.wf.modules.core.services.repositories.ClientRepository;
import net.bndy.wf.modules.core.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.modules.core.services.UserService;

@Service
@Transactional
public class OAuthService {

	@Autowired
    UserRepository userRepository;
	@Autowired
    ClientRepository clientRepository;

	@Autowired
	UserService userService;

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return this.userRepository.findByUsername(auth.getName());
		}
		return null;
	}
}

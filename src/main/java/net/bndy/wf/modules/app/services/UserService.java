/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.services;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.repositories.ClientUserRepository;
import net.bndy.wf.modules.app.services.repositories.RoleRepository;
import net.bndy.wf.modules.app.services.repositories.UserRepository;

@Service
@Transactional
public class UserService extends _BaseService<User> {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ClientUserRepository clientUserRepository;

	private Logger logger = LoggerFactory.getLogger(UserService.class);
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Page<User> getUsers(Pageable pageable) {
		return userRepo.findAll(pageable);
	}
	
	public boolean hasUsers() {
		return this.userRepo.count() > 0;
	}

	public User login(String account, String password) {
		User user = userRepo.findByUsername(account);
		if (user != null) {
			String encodedPassword = passwordEncoder.encode(password);
			if (user.getPassword().equals(password) && this.passwordEncoder.matches(password, encodedPassword)
					&& !user.isEnabled()) {
				logger.info("`{}` logged in", account);
				return user;
			}
		}

		logger.error("`{}` failed to log in", account);
		return null;
	}

	public User register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		return userRepo.saveAndFlush(user);
	}

	public User disable(Long id) {
		User u = userRepo.findOne(id);
		u.setEnabled(false);
		return userRepo.saveAndFlush(u);
	}

	public List<User> getAll() {
		return userRepo.findAll();
	}

	public User findByUsername(String username) {
		return this.userRepo.findByUsername(username);
	}
	
	public List<ClientUser> getClients(long userId) {
		return this.clientUserRepository.findByUserId(userId);
	}
	
	public void removeClient(String clientId, long userId){
		this.clientUserRepository.deleteByUserIdAndClientId(userId, clientId);
	}

	@Override
	public User save(User entity) {
		entity.setEnabled(true);
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setRoles(new HashSet<>(roleRepository.findAll()));
		return super.save(entity);
	}

}

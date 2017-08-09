package net.bndy.wf.modules.app.services;

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

import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.repositories.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public Page<User> getUsers(Pageable pageable) {
		return userRepo.findAll(pageable);
	}
	
	public boolean login(String account, String password) {
		List<User> users = userRepo.findByUserName(account);
		if(users.size() == 1) {
			User user = users.get(0);
			String encodedPassword = passwordEncoder.encode(password);
			if(user.getPassword().equals(password) && this.passwordEncoder.matches(password, encodedPassword) && !user.isDisabled()){
				logger.info("`{}` logged in", account);
				return true;
			}
		}
		
		logger.error("`{}` failed to log in", account);
		return false;
	}
	
	public User register(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDisabled(false);
		return userRepo.saveAndFlush(user);
	}
	
	public User disable(Long id){
		User u = userRepo.findOne(id);
		u.setDisabled(true);
		return userRepo.saveAndFlush(u);
	}
	
	public List<User> getAll() {
		return userRepo.findAll();
	}
}

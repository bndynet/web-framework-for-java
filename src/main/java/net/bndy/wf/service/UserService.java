package net.bndy.wf.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.domain.User;
import net.bndy.wf.repository.UserRepository;

public class UserService {
	@Autowired
	private UserRepository userRepo;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public boolean login(String account, String password){
		List<User> users = userRepo.findByUserName(account);
		if(users.size() == 1) {
			User user = users.get(0);
			if(user.getPassword() == password && !user.isDisabled()){
				logger.info("`{}` logged in", account);
				return true;
			}
		}
		logger.error("`{}` failed to log in", account);
		return false;
	}
}

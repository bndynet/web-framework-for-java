package net.bndy.wf.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.bndy.wf.modules.core.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.modules.core.services.UserService;

public class UserServiceTest extends _Test {
	@Autowired
	UserService userService;

	@Test
	public void registerAndLogin() {
		String timestamp = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		User u = new User();
		String password = "P" + timestamp;
		u.setUsername("Bendy" + timestamp);
		u.setPassword(password);
		u =  userService.register(u);
		if (u.getId() <= 0) {
			fail("failed to register new user");
		}
		u = userService.login(u.getUsername(), password);
		Assert.assertNotNull(u);
	}
}

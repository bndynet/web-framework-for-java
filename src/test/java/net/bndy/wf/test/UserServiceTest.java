package net.bndy.wf.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.UserService;

public class UserServiceTest extends _Test {
	@Autowired
	UserService userService;

	@Test
	public void registerAndLogin() {
		String timestamp = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		User u = new User();
		u.setUsername("Bendy" + timestamp);
		u.setPassword("P" + timestamp);
		u =  userService.register(u);
		if (u.getId() <= 0) {
			fail("failed to register new user");
		}
		u = userService.login(u.getUsername(), u.getPassword());
		Assert.assertNotNull(u);
	}
}

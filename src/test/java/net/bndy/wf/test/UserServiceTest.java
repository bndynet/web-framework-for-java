package net.bndy.wf.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.domain.User;
import net.bndy.wf.service.UserService;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages={"net.bndy.wf.service"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserServiceTest {
	@Autowired
	UserService userService;

	@Test
	public void registerAndLogin() {
		String timestamp = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		User u = new User();
		u.setUserName("Bendy" + timestamp);
		u.setPassword("P" + timestamp);
		u =  userService.register(u);
		if (u.getId() <= 0) {
			fail("failed to register new user");
		}
		boolean result = userService.login(u.getUserName(), u.getPassword());
		Assert.assertEquals(result, true);
	}
}

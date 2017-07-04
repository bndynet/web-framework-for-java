package net.bndy.wf.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.bndy.wf.domain.User;
import net.bndy.wf.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserServiceTest {
	@Autowired
	UserRepository userRepo;

	@Test
	public void register() {
		User u = new User();
		u.setUserName("Bendy");
		u.setPassword("Bendy");
		u = userRepo.saveAndFlush(u);
		if (u.getId() <= 0) {
			fail();
		}
	}
}

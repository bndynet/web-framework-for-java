package net.bndy.wf.modules.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.modules.app.models.UserProfile;
import net.bndy.wf.modules.app.services.AppUserProfileRepository;

@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {
	@Autowired
	private AppUserProfileRepository userProfileRepo;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public UserProfile get(@PathVariable long id) {
		return userProfileRepo.findOne(id);
}
}

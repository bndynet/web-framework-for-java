package net.bndy.wf.modules.app.services;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bndy.wf.modules.app.models.UserProfile;

public interface AppUserProfileRepository extends JpaRepository<UserProfile, Long> {

}

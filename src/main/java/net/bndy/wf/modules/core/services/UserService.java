/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services;

import java.util.*;

import net.bndy.wf.config.ApplicationUserRole;
import net.bndy.wf.modules.core.models.Role;
import net.bndy.wf.modules.core.models.User;
import net.bndy.wf.modules.core.models.UserProfile;
import net.bndy.wf.modules.core.services.repositories.UserProfileRepository;
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
import net.bndy.wf.modules.core.services.repositories.RoleRepository;
import net.bndy.wf.modules.core.services.repositories.UserRepository;

@Service
@Transactional
public class UserService extends _BaseService<User> {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Page<User> getUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public boolean hasUsers() {
        return this.userRepo.count() > 0;
    }

    public UserProfile getUserProfile(long userId) {
        return this.userProfileRepository.findByUserId(userId);
    }

    public User login(String account, String password) {
        User user = userRepo.findByUsername(account);
        if (user != null) {
            if (this.passwordEncoder.matches(password, user.getPassword())
                    && user.isEnabled()) {
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

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    public User findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    public User changeRole(long userId, long roleId) {
        this.userRepo.deleteRolesByUserId(userId);
        Role role = this.roleRepository.findOne(roleId);
        User user = this.userRepo.findOne(userId);
        if (user != null && role != null) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }
        return super.save(user);
    }

    @Override
    public User save(User entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        if (entity.getAvatar() == null || entity.getAvatar() == "") {
            entity.setAvatar(applicationConfig.getDefaultUserAvatar());
        }

        if (this.userRepo.findAll().size() == 0) {
            entity.setEnabled(true);
            // initialize roles
            roleRepository.deleteAll();
            Role adminRole = null;
            for (ApplicationUserRole role : ApplicationUserRole.values()) {
                Role roleModel = new Role();
                roleModel.setName(role.name());
                roleModel = roleRepository.saveAndFlush(roleModel);
                if (adminRole == null) {
                    adminRole = roleModel;
                }
            }

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            entity.setRoles(roles);
        }
        return super.save(entity);
    }

}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services;

import java.nio.file.Paths;
import java.util.*;

import net.bndy.wf.ApplicationContext;
import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.modules.core.models.File;
import net.bndy.wf.modules.core.models.Role;
import net.bndy.wf.modules.core.models.User;
import net.bndy.wf.modules.core.models.UserProfile;
import net.bndy.wf.modules.core.services.repositories.FileRepository;
import net.bndy.wf.modules.core.services.repositories.UserProfileRepository;
import net.bndy.wf.service.AppService;
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
    private UserRepository userRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private AppService appService;
    @Autowired
    private RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Page<User> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public Page<User> searchUsers(String keywords, Pageable pageable) {
        if (keywords != null && !keywords.isEmpty()) {
            return this.userRepository.search(keywords, pageable);
        } else {
            return this.getUsers(pageable);
        }
    }

    public boolean hasUsers() {
        return this.userRepository.count() > 0;
    }

    public UserProfile getUserProfile(long userId) {
        return this.userProfileRepository.findByUserId(userId);
    }

    public User login(String account, String password) {
        User user = userRepository.findByUsername(account);
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

    public String encodePassword(String pwd) {
        return this.passwordEncoder.encode(pwd);
    }

    public User register(User user) {
        // TODO: check username exists
        user.setPassword(this.encodePassword(user.getPassword()));
        user.setEnabled(true);
        return userRepository.saveAndFlush(user);
    }

    public User disable(Long id) {
        User u = userRepository.findOne(id);
        u.setEnabled(false);
        return userRepository.saveAndFlush(u);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User changeRole(long userId, List<Long> roleIds) {
        User user = this.userRepository.findOne(userId);
        this.userRepository.deleteRolesByUserId(userId);
        HashSet<Role> roles = new HashSet<>();
        for(Long id: roleIds) {
            if (id != null) {
                Role role = this.roleRepository.findOne(id);
                if (user != null && role != null) {
                    roles.add(role);
                }
            }
        }
        user.setRoles(roles);
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

        if (this.userRepository.findAll().size() == 0) {

            this.appService.initBasicData();

            entity.setEnabled(true);
            entity.setSuperAdmin(true);
            Role adminRole = this.roleService.findByName(applicationConfig.getAdminRole()[0]);
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            entity.setRoles(roles);
        }
        return super.save(entity);
    }

    public User updateAvatar(long userId, File newFile) {
        User u = this.get(userId);
        if (u != null && newFile != null && u.getAvatar() != null) {
            File originFile = this.fileRepository.findByUuid(u.getAvatar());

            u.setAvatar(newFile.getUuid());
            u = this.save(u);
            ApplicationContext.updateCurrentUserAvatar(u.getAvatar());

            // delete origin avatar
            if (originFile != null) {
                java.io.File f = new java.io.File(Paths.get(this.applicationConfig.getUploadPath(), originFile.getPath()).toAbsolutePath().toString());
                if (f.exists() && f.isFile()) {
                    f.delete();
                }
                this.fileRepository.delete(originFile.getId());
            }
        }
        return u;
    }

    public boolean comparePassword(String pwd, String encodedPassword) {
       return this.passwordEncoder.matches(pwd, encodedPassword);
    }

    public void changePassword(long userId, String newPassword) {
        User u = this.userRepository.getOne(userId);
        if (u != null) {
            u.setPassword(this.passwordEncoder.encode(newPassword));
            this.userRepository.saveAndFlush(u);
        }
    }
}

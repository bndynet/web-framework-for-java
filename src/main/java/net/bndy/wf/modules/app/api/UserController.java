/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.*;
import net.bndy.wf.modules.app.services.UserService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Api(value = "User API")
@RestController
@RequestMapping("/api/v1/app/users")
public class UserController extends _BaseApi<User> {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation(value = "Gets current user")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public HashMap<String, Object> me(HttpServletRequest request) throws MalformedURLException {
        User u = getCurrentUser();
        if (u != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", u.getUsername());
            map.put("avatar", new URL(new URL(request.getRequestURL().toString()), u.getAvatar()).toString());
            map.put("roles", u.getRoles());
            return map;
        }
        return null;
    }

    @ApiOperation(value = "Login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody User user, HttpServletRequest request) {
        User u = this.userService.login(user.getUsername(), user.getPassword());
        if (u != null) {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "Logout")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }

    @ApiOperation(value = "Get all authorized apps for current user")
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<ClientUser> getClients() {
        return this.userService.getClients(this.getCurrentUser().getId());
    }

    @ApiOperation(value = "Cancel authorization for specified app")
    @RequestMapping(value = "/removeapp", method = RequestMethod.PUT)
    public void removeClient(@RequestParam(name = "clientId") String clientId) {
        if (this.getCurrentUser() != null) {
            this.userService.removeClient(clientId, this.getCurrentUser().getId());
        }
    }

    @ApiOperation(value = "Enable or disable user")
    @RequestMapping(value = "/{userId}/toggleenabled", method = RequestMethod.PUT)
    public void toggleEnabled(@PathVariable(name = "userId") long userId) {
        User user = this.userService.get(userId);
        if (user != null) {
            user.setEnabled(!user.isEnabled());
            this.userService.save(user);
        }
    }

    @ApiOperation(value = "Change role for user")
    @RequestMapping(value = "/{userId}/changerole", method = RequestMethod.PUT)
    public void changeRole(@PathVariable(name = "userId") long userId, @RequestParam(name = "roleId") long roleId) {
        this.userService.changeRole(userId, roleId);
    }

    @Override
    public Page<User> get(@PageableDefault(value = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return super.get(pageable);
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.ApplicationUserRole;
import net.bndy.wf.modules.app.models.Role;
import net.bndy.wf.modules.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Configurations API")
@RestController
@RequestMapping("/api/v1/app/config")
public class ConfigController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Gets all defined roles")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getRoles() {
        List<Role> result = this.userService.getAllRoles();
        for (Role role : result) {
            ApplicationUserRole appUserRole = ApplicationUserRole.valueOf(role.getName());
            if (appUserRole != null) {
                role.setDescription(appUserRole.getDescription());
            }
        }
        return result;
    }
}

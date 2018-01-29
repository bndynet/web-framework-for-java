package net.bndy.wf.modules.core.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.lib.LongsWrapper;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.core.models.Role;
import net.bndy.wf.modules.core.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Role API")
@RestController
@RequestMapping({"/api/core/roles", "/api/v1/core/roles"})
public class RoleController extends _BaseApi<Role> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ApplicationConfig applicationConfig;

    @ApiOperation(value = "Assigns menus to role")
    @RequestMapping(value = "/{id}/assignMenus", method = RequestMethod.PUT)
    public void assignMenus(@PathVariable(name = "id") long id, @RequestBody LongsWrapper menuIds) {
        this.roleService.assignMenus(id, menuIds.getValues());
    }

    @Override
    public List<Role> get() {
        List<Role> result = super.get();

        for(Role r: result) {
           if (r.getName().equals(applicationConfig.getAdminRoleName())) {
               r.setSys(true);
           }
        }

        return result;
    }
}

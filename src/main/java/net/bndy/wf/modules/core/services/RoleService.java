package net.bndy.wf.modules.core.services;

import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.core.models.Role;
import net.bndy.wf.modules.core.services.repositories.RoleRepository;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService extends _BaseService<Role> {

    @Autowired
    private RoleRepository roleRepository;

    public void assignMenus(long roleId, List<Long> menuIds) {
        List<String> ids = new ArrayList<>(menuIds.size());
        for (Long l : menuIds) {
            if (l != null) {
                ids.add(String.valueOf(l));
            }
        }
        this.roleRepository.updateMenus(roleId, Strings.join(ids).with(","));
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public void initSysRoles() {
        Role role = this.findByName(applicationConfig.getAdminRole()[0]);
        if (role == null) {
            role = new Role();
            role.setName(applicationConfig.getAdminRole()[0]);
            if (applicationConfig.getAdminRole().length > 1) {
                role.setDescription(applicationConfig.getAdminRole()[1]);
            }
            this.roleRepository.saveAndFlush(role);
        }
    }

    public List<Role> getListWithDetail() {
        List<Role> result = this.roleRepository.findAll();
        for (Role r : result) {
            r.setUserCount(this.roleRepository.countByRoleId(r.getId()));
            if (r.getName().equals(applicationConfig.getAdminRole()[0])) {
                r.setSys(true);
            }
        }
        return result;
    }
}

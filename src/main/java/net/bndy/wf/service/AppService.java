package net.bndy.wf.service;

import net.bndy.wf.modules.core.services.ClientService;
import net.bndy.wf.modules.core.services.MenuService;
import net.bndy.wf.modules.core.services.RoleService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AppService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private net.bndy.wf.modules.cms.Setup cmsSetup;

    public List<String> allModules() {
        List<String> modules = new ArrayList<>();

        try {
            File rootModule = new ClassPathResource("/static/apps/admin/modules").getFile();
            for (File f : FileUtil.listFiles(rootModule, (pathname -> pathname.getName().toLowerCase().endsWith(".html")))) {
                String moduleName = f.getPath()
                    .replace(".html", "")
                    .replace(rootModule.getAbsolutePath(), "")
                    .replaceAll("[\\\\/]+", "-")
                    .replaceAll("^-", "");

                modules.add(moduleName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public void initBasicData() {

        this.roleService.initSysRoles();

        if (this.menuService.getAll().size() == 0) {
            this.menuService.initMenus();
        }

        if (this.clientService.getAll().size() == 0) {
            this.clientService.saveClient(null, "BNDY-NET",
                "http://bndy.net/img/apple-touch-icon-120x120-precomposed.png",
                "http://bndy.net/demo/oauth/index.html", "all", "bndy.net", "bndy.net_secret");
        }

        this.cmsSetup.init();
    }
}

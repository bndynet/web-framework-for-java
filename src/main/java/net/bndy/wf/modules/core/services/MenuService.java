/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.services;

import java.util.*;

import javax.transaction.Transactional;

import net.bndy.lib.StringHelper;
import net.bndy.wf.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bndy.wf.lib._BaseService;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.repositories.MenuRepository;
import org.thymeleaf.util.ArrayUtils;

@Service
@Transactional
public class MenuService extends _BaseService<Menu> {

    @Autowired
    private AppService appService;
    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private net.bndy.wf.modules.cms.Setup cmsSetup;
    @Autowired
    private net.bndy.wf.modules.core.Setup coreSetup;

    public List<Menu> getTemplates() {
        List<Menu> result = new ArrayList<>();

        List<String> menus = this.appService.allModules();

        Map<String, Menu> pathMapping = new HashMap<String, Menu>();
        for (String menu : menus) {
            // exclude modules
            if (menu.startsWith("shared")) {
                continue;
            }
            for (String path : StringHelper.stairSplit(menu, "-")) {
                if (pathMapping.get(path) != null) {
                    continue;
                }

                String name = "admin.modules." + path.replace("-", ".") + ".title";
                Menu m = new Menu();
                m.setVisible(true);
                m.setIcon("fa fa-fw fa-circle-thin");
                m.setName(name);
                if (path.equals(menu)) {
                    m.setLink(path);
                }
// uncomment for getting tree
//                if (path.indexOf("-") > 0) {
//                    Menu parentMenu = pathMapping.get(path.substring(0, path.lastIndexOf("-")));
//                    if (parentMenu != null) {
//                        parentMenu.getChildren().add(m);
//                    }
//                } else {
//                    result.add(m);
//                }
                // comment for getting tree
                result.add(m);

                pathMapping.put(path, m);
            }
        }

        return result;
    }

    public void initMenus() {
        if (this.getAll().size() == 0) {
            List<String> menus = this.appService.allModules();

            menus.sort(Comparator.naturalOrder());

            Map<String, Menu> pathMapping = new HashMap<String, Menu>();
            for (String menu : menus) {
                if (menu.startsWith("shared")
                    || ArrayUtils.contains(this.coreSetup.MODULES_EXCLUDED, menu)
                    || ArrayUtils.contains(this.cmsSetup.MODULES_EXCLUDED, menu)) {
                    continue;
                }

                for (String path : StringHelper.stairSplit(menu, "-")) {
                    if (pathMapping.get(path) != null) {
                        continue;
                    }

                    String name = "admin.modules." + path.replace("-", ".") + ".title";
                    Menu m = this.menuRepo.findByName(name);
                    if (m == null) {
                        m = new Menu();
                        m.setVisible(true);
                        m.setIcon("fa fa-fw fa-circle-thin");
                        m.setName(name);

                        if (path.indexOf("-") > 0) {
                            Menu parentMenu = pathMapping.get(path.substring(0, path.lastIndexOf("-")));
                            if (parentMenu != null) {
                                m.setParentId(parentMenu.getId());
                            }
                        }

                        if (path.equals(menu)) {
                            m.setLink(path);
                        }
                        m = this.save(m);
                    }
                    pathMapping.put(path, m);
                }
            }
        }
    }

    public List<Menu> getAllMenuList() {
        return this.menuRepo.findAll();
    }

    public List<Menu> getVisibleMenuList() {
        return this.menuRepo.getVisibleMenus();
    }

    public List<Menu> convertList2Tree(List<Menu> menuList) {
        List<Menu> rootMenus = new ArrayList<Menu>();
        for (Menu m : menuList) {
            if (m.getParentId() == null || m.getParentId() <= 0) {
                m.setChildren(this.getChildrenMenus(m, menuList));
                rootMenus.add(m);
            }
        }
        return rootMenus;
    }

    public Menu getMenu(long id) {
        return this.menuRepo.findOne(id);
    }

    public Menu getByName(String name) {
        return this.menuRepo.findByName(name);
    }

    public Menu getMenuManagementEntry() {
        Menu m = new Menu();
        m.setName("admin.modules.core.menus.title");
        m.setLink("core-menus");
        m.setIcon("fa fa-fw fa-circle-o text-red");
        m.setDisplayOrder(999999);
        m.setVisible(true);
        return m;
    }

    private List<Menu> getChildrenMenus(Menu menu, List<Menu> menus) {
        List<Menu> result = new ArrayList<Menu>();
        for (Menu m : menus) {
            if (m.getParentId() == menu.getId()) {
                m.setChildren(this.getChildrenMenus(m, menus));
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public Menu save(Menu menu) {
        if (menu.getParentId() != null) {
            Menu parent = this.menuRepo.findOne(menu.getParentId());
            if (parent != null) {
                menu.setParents((parent.getParents() == null ? "/" : parent.getParents()) + parent.getId() + "/");
            }
        } else {
            menu.setParents("/");
        }
        Menu result = this.menuRepo.saveAndFlush(menu);
        return result;
    }

    @Override
    public boolean delete(long id) {
        Menu m = this.menuRepo.findOne(id);
        if (m != null) {
            this.menuRepo.deleteByParents(m.getParents() + m.getId() + "/");
            return super.delete(id);
        }
        return false;
    }

    public void toggleVisible(long id) {
        Menu m = this.get(id);
        if (m != null) {
            m.setVisible(!m.isVisible());
            m = this.menuRepo.saveAndFlush(m);
            if (m.isVisible()) {
                syncParentsVisible(m);
            } else {
                syncChildrenVisible(m);
            }
        }
    }

    public void syncParentsVisible(Menu menu) {
        List<Long> parentIds = StringHelper.splitToLong(menu.getParents(), "/");
        if (parentIds.size() > 0) {
            this.menuRepo.syncParentsVisible(menu.getId(), parentIds);
        }
    }

    public void syncChildrenVisible(Menu parent) {
        if (parent != null) {
            String path = parent.getParents() == null ? "" : parent.getParents() + parent.getId() + "/";
            List<Menu> allChildren = this.menuRepo.getAllChildren(path);
            for (Menu cm : allChildren) {
                cm.setVisible(parent.isVisible());
                this.menuRepo.saveAndFlush(cm);
            }
        }
    }
}

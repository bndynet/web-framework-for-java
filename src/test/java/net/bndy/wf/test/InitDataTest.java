package net.bndy.wf.test;

import java.io.IOException;
import java.util.List;

import net.bndy.wf.modules.core.services.ClientService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.bndy.wf.lib.AppBoType;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.MenuService;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.Comment;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.repositories.PageRepository;

public class InitDataTest extends _Test {

    @Autowired
    MenuService menuService;
    @Autowired
    ClientService clientService;

    @Test
    public void initSeed() {
        initMenu();
        initOauth();
        Assert.assertTrue(this.menuService.getAllMenuList().size() > 0);
        Assert.assertTrue(this.clientService.getAll().size() > 0);
    }

    private void initMenu() {
        if (this.menuService.getAllMenuList().size() == 0) {
            try {
                this.menuService.initMenus();
            } catch (IOException e) {

            }
        }
    }

    private void initOauth() {
        if (this.clientService.getAll().size() == 0) {
            this.clientService.saveClient(null, "BNDY-NET",
                "http://bndy.net/assets/img/apple-touch-icon-120x120-precomposed.png",
                "http://bndy.net/demo/oauth/index.html", "all", "bndy.net", "bndy.net_secret");
        }
    }
}

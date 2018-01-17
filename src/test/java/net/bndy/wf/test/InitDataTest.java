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
    ArticleService articleService;
    @Autowired
    PageRepository pageRepo;
    @Autowired
    ClientService clientService;

    @Test
    public void initSeed() {
        initMenu();
//        initMenuData();
        initOauth();
        Assert.assertTrue(this.menuService.getList().size() > 0);
        Assert.assertTrue(this.clientService.getAll().size() > 0);
    }

    private void initMenu() {
        if (this.menuService.getList().size() == 0) {
            try {
                this.menuService.initMenus();
            } catch (IOException e) {

            }
        }
    }
//
//    private void initMenuData() {
//        List<Menu> menus = this.menuService.getList();
//        for (Menu m : menus) {
//            AppBoType boType = m.getBoType();
//            if (boType != null) {
//                Comment comment = new Comment();
//                switch (m.getBoType()) {
//                    case CMS_PAGE:
//                        Page p = this.pageRepo.getByBoTypeId(m.getBoTypeId());
//                        p.setContent("Content for " + m.getName());
//                        p = this.pageRepo.saveAndFlush(p);
//                        comment.setBoId(p.getId());
//                        comment.setTitle("Comment Title for " + p.getTitle());
//                        comment.setContent("Comment Content for " + p.getTitle());
//                        this.articleService.addComment(comment, p.getId());
//                        break;
//
//                    case CMS_ARTICLE:
//                        for (int i = 0; i < 15; i++) {
//                            Article article = new Article();
//                            article.setBoTypeId(m.getBoTypeId());
//                            article.setTitle(m.getName() + " - Title " + i);
//                            article.setContent("Content " + i);
//                            article = this.articleService.save(article);
//                            comment.setBoId(article.getId());
//                            comment.setTitle("Comment Title for " + article.getTitle());
//                            comment.setContent("Comment Content for " + article.getTitle());
//                            this.articleService.addComment(comment, article.getId());
//                        }
//                        break;
//
//                    default:
//                }
//            }
//        }
//    }

    private void initOauth() {
        if (this.clientService.getAll().size() == 0) {
            this.clientService.saveClient(null, "BNDY-NET",
                "http://bndy.net/assets/img/apple-touch-icon-120x120-precomposed.png",
                "http://bndy.net/demo/oauth/index.html", "all", "bndy.net", "bndy.net_secret");
        }
    }
}

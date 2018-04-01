package net.bndy.wf.modules.cms;

import net.bndy.lib.IOHelper;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.BoType;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.ChannelService;
import net.bndy.wf.modules.cms.services.PageService;
import net.bndy.wf.modules.core.models.Menu;
import net.bndy.wf.modules.core.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component("cmsSetup")
public class Setup {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private PageService pageService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MenuService menuService;

    public String[] MODULES_EXCLUDED = {"cms-articles", "cms-page", "cms-resources"};
    private final static String DATA_DIR = "./mockdata/cms";
    private static Menu rootMenu;

    public void init() {
        if (rootMenu == null) {
            rootMenu = this.menuService.getByName(Config.ROOT_MENU_NAME);
        }
        this.initPages(null, null);
        this.initArticles(null, null);
    }

    private void initPages(String dataPath, Channel parentChannel) {
        if (dataPath == null) {
            dataPath = Paths.get(DATA_DIR, BoType.Page.getName().toLowerCase()).toAbsolutePath().toString();
        }
        if (!IOHelper.isDirectoryExisted(dataPath)) {
            return;
        }
        for (File f : IOHelper.getFilesAndDirectories(dataPath)) {
            if (f.getName().startsWith(".")) {
                continue;
            }
            if (f.isFile()) {
                Channel channel = new Channel();
                channel.setName(f.getName().substring(0, f.getName().lastIndexOf(".")));
                channel.setVisible(true);
                channel.setBoType(BoType.Page);
                channel.setPath("/");

                if (parentChannel != null) {
                    channel.setPath(parentChannel.getPath() + parentChannel.getId() + "/");
                }

                channel = this.channelService.save(channel);

                Menu menu = new Menu();
                menu.setParentId(rootMenu.getId());
                menu.setParents(rootMenu.getParents() + rootMenu.getId() + "/");
                menu.setName(channel.getName());
                menu.setIcon("fa fa-fw fa-tag");
                menu.setLink(Config.LINK_PAGE);
                menu.setLinkParams("{id: " + channel.getId() + "}");
                menu.setVisible(true);
                this.menuService.save(menu);

                Page page = this.pageService.getByChannelId(channel.getId());
                try {
                    page.setContent(Files.lines(Paths.get(f.getAbsolutePath().toString()))
                        .collect(Collectors.joining("\n")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.pageService.save(page);
            }
            if (f.isDirectory()) {
                Channel pChannel = new Channel();
                pChannel.setVisible(true);
                pChannel.setName(f.getName());
                pChannel.setPath("/");
                if (parentChannel != null) {
                    pChannel.setPath(parentChannel.getPath() + parentChannel.getId() + "/");
                }
                pChannel = this.channelService.save(pChannel);
                initPages(f.getAbsolutePath().toString(), pChannel);
            }
        }

    }

    private void initArticles(String dataPath, Channel channel) {
        if (dataPath == null) {
            dataPath = Paths.get(DATA_DIR, BoType.Article.getName().toLowerCase()).toAbsolutePath().toString();
        }
        if (!IOHelper.isDirectoryExisted(dataPath)) {
            return;
        }
        for (File f : IOHelper.getFilesAndDirectories(dataPath)) {
            if (f.getName().startsWith(".")) {
                continue;
            }
            if (f.isFile() && channel != null) {
                Article article = new Article();
                article.setChannelId(channel.getId());
                article.setTitle(f.getName().substring(0, f.getName().lastIndexOf(".")));
                try {
                    article.setContent(Files.lines(Paths.get(f.getAbsolutePath().toString()))
                        .collect(Collectors.joining("\n")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.articleService.save(article);
            }
            if (f.isDirectory()) {
                Channel pChannel = new Channel();
                pChannel.setVisible(true);
                pChannel.setName(f.getName());
                pChannel.setPath("/");
                pChannel.setBoType(BoType.Article);
                if (channel != null) {
                    pChannel.setPath(channel.getPath() + channel.getId() + "/");
                }
                pChannel = this.channelService.save(pChannel);

                Menu menu = new Menu();
                menu.setParentId(rootMenu.getId());
                menu.setParents(rootMenu.getParents() + rootMenu.getId() + "/");
                menu.setName(pChannel.getName());
                menu.setIcon("fa fa-fw fa-tag");
                menu.setLink(Config.LINK_ARTICLES);
                menu.setLinkParams("{id: " + pChannel.getId() + "}");
                menu.setVisible(true);
                this.menuService.save(menu);

                initArticles(f.getAbsolutePath().toString(), pChannel);
            }
        }

    }
}

package net.bndy.wf.modules.cms;

import net.bndy.lib.IOHelper;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.BoType;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.ChannelService;
import net.bndy.wf.modules.cms.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@Qualifier("cmsSetup")
public class Setup {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private PageService pageService;
    @Autowired
    private ArticleService articleService;

    private final static String DATA_DIR = "./mockdata/cms";

    public void init() {
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
                initArticles(f.getAbsolutePath().toString(), pChannel);
            }
        }

    }
}

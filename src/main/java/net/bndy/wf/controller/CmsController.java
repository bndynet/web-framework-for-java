package net.bndy.wf.controller;

import net.bndy.ftsi.SearchResult;
import net.bndy.lib.StringHelper;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.cms.IndexModel;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.ChannelService;
import net.bndy.wf.modules.cms.services.PageService;
import net.bndy.wf.modules.cms.services.ResourceService;
import net.bndy.wf.modules.core.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CmsController extends _BaseController {

    @Autowired
    private PageService pageService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/page/{channel}")
    public String page(Model viewModel,
                       @PathVariable(name = "channel") String channel) throws NoResourceFoundException {
        Channel channel1 = this.channelService.getByNameOrNameKey(channel);
        Page page = null;
        if (channel1 == null) {
            if (StringHelper.isNumeric(channel)) {
                page = this.pageService.get(Integer.parseInt(channel));
            }
        } else {
            page = this.pageService.getByChannelId(channel1.getId());
        }
        if (page == null) {
            throw new NoResourceFoundException();
        }
        viewModel.addAttribute("pageTitle", page.getTitle());
        viewModel.addAttribute("model", page);
        return "public/page";
    }

    @RequestMapping("/articles/{channelName}")
    public String articles(Model viewModel,
                           @PathVariable(name = "channelName") String channelName,
                           @PageableDefault Pageable pageable) throws NoResourceFoundException {

        Channel channel = this.channelService.getByNameOrNameKey(channelName);
        if (channel != null) {
            viewModel.addAttribute("channel", channel);
            org.springframework.data.domain.Page<Article> pager = this.articleService.findByChannelId(channel.getId(), pageable);
            viewModel.addAttribute("model", pager);
        } else {
            throw new NoResourceFoundException();
        }

        return "public/articles";
    }

    @RequestMapping("/article/{key}")
    public String article(Model viewModel,
                          @PathVariable(name = "key") String key) throws NoResourceFoundException {

        Article article = this.articleService.getByTitleKey(key);
        if (article == null && StringHelper.isNumeric(key)) {
            article = this.articleService.get(Long.parseLong(key));
        }
        if (article != null) {
            viewModel.addAttribute("pageTitle", article.getTitle());
            viewModel.addAttribute("model", article);
        } else {
            throw new NoResourceFoundException();
        }
        return "public/article";
    }

    @RequestMapping(value = "/search")
    public String search(Model viewModel,
                         @RequestParam(name = "q", required = false) String keywords,
                         @RequestParam(name = "page", required = false) Integer page
    ) {

        SearchResult<IndexModel> searchResult = null;
        if (page == null) {
            page = 1;
        }

        if (!StringHelper.isNullOrWhiteSpace(keywords)) {
            searchResult = ApplicationContext.getIndexService().search(keywords, IndexModel.class, page, 10);
        }

        viewModel.addAttribute("pageTitle", keywords);
        viewModel.addAttribute("model", searchResult);
        viewModel.addAttribute("keywords", keywords);
        return "public/search";
    }

    @RequestMapping("/resources/{channelName}")
    public String resources(Model viewModel,
                           @PathVariable(name = "channelName") String channelName) throws NoResourceFoundException {

        Channel channel = this.channelService.getByNameOrNameKey(channelName);
        if (channel != null) {
            viewModel.addAttribute("channel", channel);
            List<File> files = this.resourceService.getFilesByChannelId(channel.getId());
            viewModel.addAttribute("files", files);
        } else {
            throw new NoResourceFoundException();
        }

        return "public/resources";
    }
}

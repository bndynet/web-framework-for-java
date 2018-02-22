package net.bndy.wf.controller;

import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.cms.models.Article;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.ArticleService;
import net.bndy.wf.modules.cms.services.ChannelService;
import net.bndy.wf.modules.cms.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CmsController extends _BaseController {

    @Autowired
    private PageService pageService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/page/{channelTitle}")
    public String page(Model viewModel,
                       @PathVariable(name = "channelTitle") String title,
                       @RequestParam(name = "id", required = false) Long id) throws NoResourceFoundException {
        Page p = this.pageService.getByTitle(title);
        if (p == null || (id != null && p.getChannelId() != id)) {
            throw new NoResourceFoundException();
        }
        viewModel.addAttribute("model", p);
        return "/cms/page";
    }

    @RequestMapping("/articles/{channelName}")
    public String articles(Model viewModel,
                           @PathVariable(name = "channelName") String channelName,
                           @PageableDefault Pageable pageable) throws NoResourceFoundException {

        Channel channel = this.channelService.getByName(channelName);
        if (channel != null) {
            viewModel.addAttribute("channel", channel);
            org.springframework.data.domain.Page<Article> pager = this.articleService.findByChannelId(channel.getId(), pageable);
            viewModel.addAttribute("model", pager);
        } else {
            throw new NoResourceFoundException();
        }

        return "/cms/articles";
    }

    @RequestMapping("/article/{key}")
    public String article(Model viewModel,
                          @PathVariable(name = "key") String key) throws NoResourceFoundException {

        Article article = key.matches("^[0-9]*$") ? this.articleService.get(Long.parseLong(key))
            : this.articleService.getByTitleKey(key);
        if (article != null) {
            viewModel.addAttribute("model", article);
        } else {
            throw new NoResourceFoundException();
        }
        return "/cms/article";
    }
}

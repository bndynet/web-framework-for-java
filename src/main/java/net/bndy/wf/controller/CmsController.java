package net.bndy.wf.controller;

import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CmsController extends _BaseController {

    @Autowired
    private PageService pageService;

    @RequestMapping("/page/{title}")
    public String page(Model viewModel,
                       @PathVariable(name = "title") String title,
                       @RequestParam(name = "id", required = false) Long id) throws NoResourceFoundException {
        Page p = this.pageService.getByTitle(title);
        if (p == null || (id != null && p.getChannelId() != id)) {
            throw new NoResourceFoundException();
        }
        viewModel.addAttribute("model", p);
        return "/cms/page";
    }
}

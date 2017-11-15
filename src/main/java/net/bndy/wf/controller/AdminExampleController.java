/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/example")
public class AdminExampleController extends _BaseController {

    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "admin/example/dashboard";
    }

    @RequestMapping(value = "/dashboard1")
    public String dashboard1() {
        return "admin/example/dashboard1";
    }

    @RequestMapping(value = "/widgets")
    public String widgets() {
        return "admin/example/widgets";
    }

    @RequestMapping(value = "/calendar")
    public String canlendar() {
        return "admin/example/calendar";
    }

    @RequestMapping(value = "/forms/advanced")
    public String formsadvanced() {
        return "admin/example/forms/advanced";
    }
    @RequestMapping(value = "/forms/editors")
    public String formsEditors() {
        return "admin/example/forms/editors";
    }
    @RequestMapping(value = "/forms/general")
    public String formsGeneral() {
        return "admin/example/forms/general";
    }
    @RequestMapping(value = "/mailbox/compose")
    public String mailboxCompose() {
        return "admin/example/mailbox/compose";
    }
    @RequestMapping(value = "/mailbox/mailbox")
    public String mailboxMailbox() {
        return "admin/example/mailbox/mailbox";
    }
    @RequestMapping(value = "/mailbox/read-mail")
    public String mailboxReadMail() {
        return "admin/example/mailbox/read-mail";
    }
    @RequestMapping(value = "/tables/data")
    public String tablesData() {
        return "admin/example/tables/data";
    }
    @RequestMapping(value = "/tables/simple")
    public String tablesSimple() {
        return "admin/example/tables/simple";
    }
    @RequestMapping(value = "/UI/buttons")
    public String uiButtons() {
        return "admin/example/UI/buttons";
    }
    @RequestMapping(value = "/UI/general")
    public String uiGeneral() {
        return "admin/example/UI/general";
    }
    @RequestMapping(value = "/UI/icons")
    public String uiIcons() {
        return "admin/example/UI/icons";
    }
    @RequestMapping(value = "/UI/modals")
    public String uiModals() {
        return "admin/example/UI/modals";
    }
    @RequestMapping(value = "/UI/sliders")
    public String uiSliders() {
        return "admin/example/UI/sliders";
    }
    @RequestMapping(value = "/UI/timeline")
    public String uiTimeline() {
        return "admin/example/UI/timeline";
    }

    @RequestMapping(value = "/pages/404")
    public String pages404() {
        return "admin/example/pages/404";
    }
    @RequestMapping(value = "/pages/500")
    public String pages500() {
        return "admin/example/pages/500";
    }
    @RequestMapping(value = "/pages/blank")
    public String pagesBlank() {
        return "admin/example/pages/blank";
    }
    @RequestMapping(value = "/pages/invoice")
    public String pagesInvoice() {
        return "admin/example/pages/invoice";
    }
    @RequestMapping(value = "/pages/pace")
    public String pagesPace() {
        return "admin/example/pages/pace";
    }
    @RequestMapping(value = "/pages/profile")
    public String pagesProfile() {
        return "admin/example/pages/profile";
    }
}

package net.bndy.wf.modules.cms.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.cms.models.Page;

@Api(value = "Page API")
@RestController
@RequestMapping("/api/v1/cms/pages")
public class PageController extends _BaseApi<Page> {

}

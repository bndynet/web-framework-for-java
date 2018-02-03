package net.bndy.wf.modules.cms.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.modules.cms.models.BoType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "API for CMS Configuration")
@RestController
@RequestMapping({"/api/cms/config", "/api/v1/cms/config"})
public class CmsConfigController {

    @ApiOperation(value = "Get all definitions of BoType")
    @RequestMapping(value = "boTypes", method = RequestMethod.GET)
    public List<BoType> getBoTypes() {
        List<BoType> result = new ArrayList<>();
        for (BoType bt : BoType.values()) {
            result.add(bt);
        }
        return result;
    }
}

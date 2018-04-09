/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.jalokim.propertiestojson.util.PropertiesToJsonParser;

import javax.cache.annotation.CacheResult;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Configurations API")
@RestController
@RequestMapping({"/api/core/config", "/api/v1/core/config"})
public class ConfigController {

    @ApiOperation(value = "Gets all messages of current locale")
    @CacheResult(cacheName = "default")
    @RequestMapping(value = "/lang", method = RequestMethod.GET)
    public Object lang(@RequestParam(name = "lang") String lang) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> enumeration = ApplicationContext.i18n().getKeys();
        for (; enumeration.hasMoreElements(); ) {
            String key = enumeration.nextElement();
            map.put(key, ApplicationContext.i18n().getString(key));
        }
        return PropertiesToJsonParser.parseToJson(map);
    }
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.api;

import io.swagger.annotations.Api;
import net.bndy.wf.ApplicationContext;
import net.bndy.wf.modules.core.models.UserProfile;
import net.bndy.wf.lib._BaseApi;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User Profile API")
@RestController
@RequestMapping({"/api/core/userProfiles", "/api/v1/core/userProfiles"})
public class UserProfileController extends _BaseApi<UserProfile> {

    @Override
    public UserProfile post(@RequestBody UserProfile entity) {
        entity.setUserId(ApplicationContext.getCurrentUser().getId());
        return super.post(entity);
    }
}

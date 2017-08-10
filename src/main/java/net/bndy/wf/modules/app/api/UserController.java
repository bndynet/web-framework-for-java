package net.bndy.wf.modules.app.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.app.models.*;

@Api(value = "User API")
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends _BaseApi<User> {

}

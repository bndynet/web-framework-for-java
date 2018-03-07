package net.bndy.wf.modules.core.api;

import io.swagger.annotations.Api;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.core.models.File;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "File Resource API")
@RestController
@RequestMapping({"/api/core/files"})
public class FileController  extends _BaseApi<File> {

}

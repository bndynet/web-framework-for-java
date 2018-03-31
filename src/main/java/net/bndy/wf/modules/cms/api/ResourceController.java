package net.bndy.wf.modules.cms.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.lib.StringHelper;
import net.bndy.wf.exceptions.DisabledFeatureException;
import net.bndy.wf.exceptions.InvalidParametersException;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.cms.models.Resource;
import net.bndy.wf.modules.cms.services.ResourceService;
import net.bndy.wf.modules.core.models.File;
import net.bndy.wf.modules.core.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Resource API")
@RestController
@RequestMapping({"/api/cms/resources", "/api/v1/cms/resources"})
public class ResourceController extends _BaseApi<Resource> {

    @Autowired
    private FileService fileService;
    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "Gets all files in specified channel")
    @RequestMapping(value = "/{channelId:\\d+}/files", method = RequestMethod.GET)
    public List<File> getFiles(@PathVariable(name = "channelId") long channelId) {
        return this.resourceService.getFilesByChannelId(channelId);
    }

    @ApiOperation(value = "Deletes file")
    @RequestMapping(value = "/files/{id:\\d+}", method = RequestMethod.DELETE)
    public void deleteFile(@PathVariable(name = "id") long id) {
        this.resourceService.deleteFile(id);
    }

    @Override
    public File upload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException, DisabledFeatureException {

        String urlChannelId = request.getParameter("channelId");
        if (StringHelper.isNullOrWhiteSpace(urlChannelId) || !StringHelper.isNumeric(urlChannelId)) {
            try {
                throw new InvalidParametersException("channelId");
            } catch (InvalidParametersException e) {
                e.printStackTrace();
            }
        }

        File f = super.upload(file, request);
        Resource r = new Resource();
        r.setChannelId(Long.parseLong(urlChannelId));
        r.setFileId(f.getId());
        this.resourceService.save(r);

        List<Long> ids = new ArrayList<>();
        ids.add(f.getId());
        this.fileService.setRef(ids);
        return f;
    }
}

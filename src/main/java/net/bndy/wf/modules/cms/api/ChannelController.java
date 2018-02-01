package net.bndy.wf.modules.cms.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.exceptions.ResourceIntegrityException;
import net.bndy.wf.lib._BaseApi;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.services.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Channel API")
@RestController
@RequestMapping({"/api/cms/channels", "/api/v1/cms/channels"})
public class ChannelController extends _BaseApi<Channel> {

    @Autowired
    private ChannelService channelService;

    @Override
    public List<Channel> get() {
        return super.get().stream()
            .filter(channel -> channel.isVisible())
            .collect(Collectors.toList());
    }

    @Override
    public void delete(@PathVariable(name = "id") long id) throws NoResourceFoundException, ResourceIntegrityException {
        int childrenCount = this.channelService.countChildren(id);
        if (childrenCount > 0) {
            throw new ResourceIntegrityException("admin.modules.cms.channels.errForDelete");
        }

        super.delete(id);
    }

    @ApiOperation(value = "Gets all channels")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public List<Channel> getWithQuery(
        @RequestParam(name = "all", required = false) boolean all) {
        return super.get();
    }

    @ApiOperation(value = "Toogles channel visible")
    @RequestMapping(value = "/{id:\\d+}/toggleVisible", method = RequestMethod.PUT)
    public void toggleVisible(@PathVariable(name = "id") long id) {
        this.channelService.toggleVisible(id);
    }
}

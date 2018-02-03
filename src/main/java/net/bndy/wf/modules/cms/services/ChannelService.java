package net.bndy.wf.modules.cms.services;

import net.bndy.lib.StringHelper;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.services.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChannelService extends _BaseService<Channel> {

    @Autowired
    private ChannelRepository channelRepository;

    private void syncVisible(Channel channel) {
        if (channel.isVisible()) {
            List<Long> ids = StringHelper.splitToLong(channel.getPath(), "/");
            if (ids.size() > 0) {
                this.channelRepository.openVisible(ids);
            }
        } else {
            this.channelRepository.hideAllChildren(channel.getPath() + channel.getId() + "/");
        }
    }

    public void toggleVisible(long channelId) {
        Channel channel = this.channelRepository.findOne(channelId);
        if (channel != null) {
            channel.setVisible(!channel.isVisible());
            channel = this.channelRepository.saveAndFlush(channel);
            syncVisible(channel);
        }
    }

    public int countChildren(long channelId) throws NoResourceFoundException {
        Channel channel = this.channelRepository.findOne(channelId);
        if (channel != null) {
            return this.channelRepository.countByPath(channel.getPath() + channel.getId() + "/");
        }

        throw new NoResourceFoundException();
    }

    @Override
    public Channel save(Channel entity) {
        if (entity.getPath() == null || "".equals(entity.getPath())) {
            entity.setPath("/");
        }
        entity = super.save(entity);
        syncVisible(entity);
        return entity;
    }

    @Override
    public boolean delete(long id) {
        // TODO: delete all resources according to boType
        return super.delete(id);
    }
}

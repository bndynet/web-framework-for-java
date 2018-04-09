package net.bndy.wf.modules.cms.services;

import net.bndy.lib.StringHelper;
import net.bndy.wf.exceptions.NoResourceFoundException;
import net.bndy.wf.modules.cms.models.Channel;
import net.bndy.wf.modules.cms.models.Page;
import net.bndy.wf.modules.cms.services.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChannelService extends _BaseService<Channel> {

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PageService pageService;
    @Autowired
    private ResourceService resourceService;

    public List<Channel> getTree(boolean all) {
        if (all) {
            return this.convert2Tree(this.channelRepository.findAll());
        } else {
            return this.convert2Tree(this.channelRepository.findByIsVisible(true));
        }
    }

    private void syncVisible(@NotNull Channel channel) {
        if (channel.isVisible()) {
            List<Long> ids = StringHelper.splitToLong(channel.getPath(), "/");
            if (ids.size() > 0) {
                this.channelRepository.openVisible(ids);
            }
        } else {
            this.channelRepository.hideAllChildren(channel.getPath() + channel.getId() + "/");
        }
    }

    public Channel getByName(String name) {
        List<Channel> result = this.channelRepository.findByName(name);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public Channel getByNameKey(String nameKey) {
        List<Channel> result = this.channelRepository.findByNameKey(nameKey);
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public Channel getByNameOrNameKey(String nameOrKey) {
        Channel result = this.getByNameKey(nameOrKey);
        if (result == null) {
            result = this.getByName(nameOrKey);
        }
        return result;
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

    public boolean hasContent(long channelId) {
        return this.pageService.countByChannelId(channelId) > 0
            || this.articleService.countByChannelId(channelId) > 0
            || this.resourceService.countByChannelId(channelId) > 0
            ;
    }

    public List<Channel> getSameTypeChannels(long sourceChannelId) {
        List<Channel> result = new ArrayList<>();
        Channel channel = this.get(sourceChannelId);
        if (channel != null && channel.getBoType() != null) {
            result = this.channelRepository.findByBoTypeValue(channel.getBoType().getValue());
        }
        return result;
    }

    public void transferChannel(long sourceId, long targetId) {
        Channel source = this.channelRepository.findOne(sourceId);
        Channel target = this.channelRepository.findOne(targetId);
        if (source != null && target != null && source.getBoType() == target.getBoType()) {
            switch (source.getBoType()) {
                case Resource:
                    this.resourceService.transfer(source.getId(), target.getId());
                    break;
                case Page:
                    this.pageService.transfer(source.getId(), target.getId());
                    break;
                case Article:
                    this.articleService.transfer(source.getId(), target.getId());
                    break;
            }
        }
    }

    public List<Channel> convert2Tree(List<Channel> all) {
        List<Channel> result = all.stream().filter((c) -> "/".equals(c.getPath()))
            .collect(Collectors.toList());

        for (Channel root : result) {
            setChildren(root, all);
        }

        return result;
    }

    private void setChildren(Channel channel, List<Channel> all) {
        channel.setChildren(all.stream().filter((c) -> (channel.getPath() + channel.getId() + "/").equals(c.getPath())).collect(Collectors.toList()));
        for (Channel c : channel.getChildren()) {
            setChildren(c, all);
        }
    }

    @Override
    public Channel save(Channel entity) {
        if (entity.getPath() == null || "".equals(entity.getPath())) {
            entity.setPath("/");
        }
        entity.setNameKey(StringHelper.title2Url(entity.getName()));
        Channel existed = this.getByNameKey(entity.getNameKey());
        entity = super.save(entity);
        if (existed != null && existed.getId() != entity.getId()) {
            entity.setNameKey(entity.getNameKey() + "-" + entity.getId());
            entity = super.save(entity);
        }
        syncVisible(entity);

        if (entity.getBoType() != null) {
            switch (entity.getBoType()) {
                case Page:
                    Page p = this.pageService.getByChannelId(entity.getId());
                    if (p == null) {
                        p = new Page();
                        p.setContent(entity.getName());
                    }
                    p.setChannelId(entity.getId());
                    p.setTitle(entity.getName());
                    this.pageService.save(p);
                    break;
                default:
                	break;
            }
        }

        return entity;
    }

    @Override
    public boolean delete(long id) {
        Channel channel = this.get(id);
        if (channel != null) {
            if (channel.getBoType() != null) {
                switch (channel.getBoType()) {
                    case Article:
                        this.articleService.deleteByChannelId(channel.getId());
                        break;
                    case Page:
                        this.pageService.deleteByChannelId(channel.getId());
                        break;
                    case Resource:
                        this.resourceService.deleteByChannelId(channel.getId());
                        break;
                }
            }
            return super.delete(id);
        }
        return false;
    }
}

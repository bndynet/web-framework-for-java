package net.bndy.wf.modules.cms.models;

import net.bndy.wf.lib._BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="cms_resource")
public class Resource extends _BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private long channelId;
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private long fileId;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }
}

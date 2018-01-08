/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.models;

import net.bndy.wf.lib.FileType;
import net.bndy.wf.lib._BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "core_file", indexes = {
    @Index(name = "idx_uuid", columnList = "uuid", unique = true)
})
public class File extends _BaseEntity {

    private String uuid;
    private String name;
    private String extName;
    private long size;
    private String path;

    @Enumerated(EnumType.ORDINAL)
    private FileType type;
    @Transient
    private String url;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File() {
        this.setUuid(UUID.randomUUID().toString());
    }
}

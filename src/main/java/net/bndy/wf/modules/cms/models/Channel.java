package net.bndy.wf.modules.cms.models;

import net.bndy.wf.lib._BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="cms_channel")
public class Channel extends _BaseEntity {

    private String name;
    private String path;
    private Long boId;
    @Enumerated(EnumType.ORDINAL)
    private BoType boType;
    private boolean isVisible;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getBoId() {
        return boId;
    }

    public void setBoId(Long boId) {
        this.boId = boId;
    }

    public BoType getBoType() {
        return boType;
    }

    public void setBoType(BoType boType) {
        this.boType = boType;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}

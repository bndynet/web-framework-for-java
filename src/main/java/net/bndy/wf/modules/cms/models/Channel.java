package net.bndy.wf.modules.cms.models;

import net.bndy.lib.StringHelper;
import net.bndy.wf.lib._BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cms_channel")
public class Channel extends _BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
    private String nameKey;
    private String path;
    @Enumerated(EnumType.ORDINAL)
    private BoType boType;
    private boolean isVisible;
    @Transient
    private boolean hasContent;
    @Transient
    private String url;
    @Transient
    private List<Channel> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (this.boType != null && (url == null || "".equals(url))) {
            switch (this.boType) {
                case Resource:
                    url = "/resources/" + (StringHelper.isNullOrWhiteSpace(this.nameKey) ? this.name : this.nameKey); // + "?id=" + this.id;
                    break;
                case Page:
                    url = "/page/"  + (StringHelper.isNullOrWhiteSpace(this.nameKey) ? this.name : this.nameKey); // + "?id=" + this.id;
                    break;
                case Article:
                    url = "/articles/"  + (StringHelper.isNullOrWhiteSpace(this.nameKey) ? this.name : this.nameKey); // + "?id=" + this.id;
                    break;
            }
        }
        return url == null ? "" : url;
    }

    public List<Channel> getChildren() {
        return children;
    }

    public void setChildren(List<Channel> children) {
        this.children = children;
    }

}

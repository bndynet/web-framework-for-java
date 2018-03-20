package net.bndy.wf.modules.cms;

import net.bndy.ftsi.Indexable;
import net.bndy.lib.StringHelper;

public class IndexModel {

    public IndexModel() {
    }

    public IndexModel(Long id, String title, String titleKey, String content, String catalog) {
        this.id = catalog + "_" + id != null ? id.toString() : StringHelper.generateUUID();
        this.title = title;
        this.titleKey = titleKey;
        this.content = content;
        this.catalog = catalog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Indexable(isKey = true)
    private String id;
    private String catalog;
    private String title;
    private String titleKey;
    private String content;
}

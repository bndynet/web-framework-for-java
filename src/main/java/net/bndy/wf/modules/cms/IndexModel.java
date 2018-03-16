package net.bndy.wf.modules.cms;

public class IndexModel {

    public IndexModel() {}

    public IndexModel(long id, String title, String titleKey, String content, String catalog) {
        this.id = id;
        this.title = title;
        this.titleKey = titleKey;
        this.content = content;
        this.catalog = catalog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    private Long id;
    private String catalog;
    private String title;
    private String titleKey;
    private String content;
}

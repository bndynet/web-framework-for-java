package net.bndy.wf.modules.cms.models;

public enum BoType {
    Page(10),
    Article(20),
    File(30);

    private int value;

    public int getValue() {
        return value;
    }

    BoType(int value) {
        this.value = value;
    }
}

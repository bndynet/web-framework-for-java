package net.bndy.wf.modules.app;

public enum AppBoType {
	CMS_PAGE(10),
	CMS_ARTICLE(20),
	CMS_FILE(30);
	
	private int value;
	AppBoType(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
}

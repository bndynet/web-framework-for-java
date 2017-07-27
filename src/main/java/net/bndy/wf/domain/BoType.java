package net.bndy.wf.domain;

public enum BoType {
	PAGE(10),
	ARTICLE(20),
	FILE(30);
	
	private int value;
	BoType(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
}

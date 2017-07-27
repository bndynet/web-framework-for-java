package net.bndy.wf.domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class AppMenu extends _BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private long boTypeId;
	private long parentId;

	private String icon;
	private String name;
	private String description;
	private boolean isHidden;
	private int displayOrder;
	
	@Enumerated(EnumType.ORDINAL)
	private BoType boType;
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BoType getBoType() {
		return boType;
	}
	public void setBoType(BoType boType) {
		this.boType = boType;
	}
	public long getBoTypeId() {
		return boTypeId;
	}
	public void setBoTypeId(long boTypeId) {
		this.boTypeId = boTypeId;
	}
	public boolean isHidden() {
		return isHidden;
	}
	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
}

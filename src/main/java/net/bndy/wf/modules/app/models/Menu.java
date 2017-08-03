package net.bndy.wf.modules.app.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;
import net.bndy.wf.modules.app.AppBoType;

@Entity
@Table(name="app_menu")
public class Menu extends _BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private long boTypeId;
	private long parentId;

	private String icon;
	private String name;
	private String description;
	private boolean isHidden;
	private int displayOrder;
	
	@Enumerated(EnumType.ORDINAL)
	private AppBoType boType;
	
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
	public AppBoType getBoType() {
		return boType;
	}
	public void setBoType(AppBoType boType) {
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

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="core_menu")
public class Menu extends _BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private Long parentId;
	private String icon;
	private String name;
	private String link;
	private String parents;
	private String linkParams;
	private boolean isVisible;
	private int displayOrder;
	
	@Transient
	private Collection<Menu> children;


	public Menu() {
		this.setChildren(new ArrayList<Menu>());
	}
	
	public Collection<Menu> getChildren() {
		return children;
	}
	public void setChildren(Collection<Menu> children) {
		this.children = children;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkParams() {
		return linkParams;
	}
	public void setLinkParams(String linkParams) {
		this.linkParams = linkParams;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getParents() {
		return parents;
	}
	public void setParents(String parents) {
		this.parents = parents;
	}
}

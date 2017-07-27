package net.bndy.wf.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Category extends _BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name;
	private long parentId;
	
	@Enumerated(EnumType.ORDINAL)
	private BoType boType;

	@Column(name = "[Group]")
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public BoType getBoType() {
		return boType;
	}

	public void setBoType(BoType boType) {
		this.boType = boType;
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_role")
public class Role extends _BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;
  
    public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.app.models;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_user")
public class User extends _BaseEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
    @JsonIgnore
    private String password;
    private boolean enabled;
    private boolean isExpired;
    private boolean isLocked;

    @Transient
    @JsonIgnore
    private String passwordConfirm;
    
    @ManyToMany
    @JoinTable(name = "app_user_role", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

	public User(){}
	
	public User(User user){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
	}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}

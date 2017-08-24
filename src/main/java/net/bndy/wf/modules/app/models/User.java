package net.bndy.wf.modules.app.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_user")
public class User extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String username;
    private String password;

    @Transient
    private String passwordConfirm;
    private boolean enabled;
    
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
}
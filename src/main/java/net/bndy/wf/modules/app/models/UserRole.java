package net.bndy.wf.modules.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_user_role")
public class UserRole extends _BaseEntity {
	private static final long serialVersionUID = 1L;
	private String role;
    private String username;
  
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
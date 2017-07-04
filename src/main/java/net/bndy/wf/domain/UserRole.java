package net.bndy.wf.domain;

import javax.persistence.Entity;

@Entity
public class UserRole extends _BaseEntity {
	private static final long serialVersionUID = 1L;
	private String role;
    private Long userId;
  
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
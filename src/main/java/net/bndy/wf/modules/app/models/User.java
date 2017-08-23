package net.bndy.wf.modules.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_user")
public class User extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String username;
    private String password;
    private boolean enabled;

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
}
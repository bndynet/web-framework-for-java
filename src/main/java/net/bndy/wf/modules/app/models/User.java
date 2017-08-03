package net.bndy.wf.modules.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="app_user")
public class User extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	private String userName;
    private String password;
    private boolean disabled;

    public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public User(){}
	
	public User(User user){
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.disabled = user.isDisabled();
	}

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
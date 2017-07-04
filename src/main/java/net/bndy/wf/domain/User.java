package net.bndy.wf.domain;

import javax.persistence.Entity;

@Entity
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
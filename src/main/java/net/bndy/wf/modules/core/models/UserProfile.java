/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.models;

import java.util.Date;

import javax.persistence.*;

import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name="core_user_profile")
public class UserProfile extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition = "BIGINT UNSIGNED", unique = true, nullable = false)
	private Long userId;
	private String email;
	private String realName;
	private String gender;
	@Column(columnDefinition = "DATE")
	private Date birthday;
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}

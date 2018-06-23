package com.youzi.MyBlog.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6309024535525815495L;
	private String id;
	private String userId;
	private String userName;
	private Date loginDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	@Override
	public String toString() {
		return "LoginLog [id=" + id + ", userId=" + userId + ", userName="
				+ userName + ", loginDate=" + loginDate + "]";
	}
	public LoginLog() {
		super();
		// TODO Auto-generated constructor stub
	}

}

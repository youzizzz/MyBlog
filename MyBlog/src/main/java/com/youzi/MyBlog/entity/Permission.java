package com.youzi.MyBlog.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Permission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5551672574855354478L;

	private String pid;
	private String pname;
	private Set<Role> role = new HashSet<Role>();
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Permission [pid=" + pid + ", pname=" + pname + ", role=" + role
				+ "]";
	}

}

package com.youzi.MyBlog.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019687824418261476L;
	
	private String rid;
	private String rname;
	private Set<User> user = new HashSet<User>();
	private Set<Permission> modules = new HashSet<Permission>();
	
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}
	public Set<Permission> getModules() {
		return modules;
	}
	public void setModules(Set<Permission> modules) {
		this.modules = modules;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Role [rid=" + rid + ", rname=" + rname + ", user=" + user
				+ ", modules=" + modules + "]";
	}

}

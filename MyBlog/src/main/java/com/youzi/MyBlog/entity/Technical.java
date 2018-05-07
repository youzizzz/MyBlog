package com.youzi.MyBlog.entity;

import java.io.Serializable;

public class Technical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7737642887054564086L;
	
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Technical() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Technical [id=" + id + ", name=" + name + "]";
	}

}

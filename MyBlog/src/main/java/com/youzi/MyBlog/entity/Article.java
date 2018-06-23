package com.youzi.MyBlog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 722580394229534300L;

	private String id;
	private String title;
	private String content;
	private String description;
	private Integer looknum;
	private Date createTime;
	private String technicals;
	private String cty;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		getThumbnailUrl();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getLooknum() {
		return looknum;
	}
	public void setLooknum(Integer looknum) {
		this.looknum = looknum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTechnicals() {
		return technicals;
	}
	public void setTechnicals(String technicals) {
		this.technicals = technicals;
	}
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCty() {
		return cty;
	}
	public void setCty(String cty) {
		this.cty = cty;
	}

	public String getThumbnailUrl() {
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		String str_src = null;
		if (result_img) {
			while (result_img) {
				// 获取到匹配的<img />标签中的内容
				String str_img = m_img.group(2);
				// 开始匹配<img />标签中的src
				Pattern p_src = Pattern
						.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					str_src = m_src.group(3);

				}
				result_img = false;
			}

		}
		return str_src == null ? "/apple.jpg" : str_src;
	}
	
	public void motifyContent() {
		this.content.replaceAll("src","th:src");
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content
				+ ", description=" + description + ", looknum=" + looknum
				+ ", createTime=" + createTime + ", technicals=" + technicals
				+ ", cty=" + cty + "]";
	}
}
package com.shopping.common.pojo;

import java.io.Serializable;

//为了返回符合easyUI的tree要求的json对象，而定义的实体类
public class EasyUITreeNode implements Serializable {

	private long id;
	private String text;
	private String state;//closed,open
	
	
	public long getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public String getState() {
		return state;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}

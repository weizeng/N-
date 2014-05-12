package com.nhaowan.mobile.base.response;

import com.haha.frame.utils.Element;

public class HomeScreenResponse {
	
	@Element(value = "ret")
	private int ret;
	
	@Element(value = "msg")
	private int msg;
	
	@Element(value = "pic_url")
	private String picUrl;
	
	@Element(value = "link_url")
	private String linkUrl;
	
	@Element(value = "other")
	private String other;
	
	@Element(value = "force")
	private int force;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getMsg() {
		return msg;
	}

	public void setMsg(int msg) {
		this.msg = msg;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	
	
}

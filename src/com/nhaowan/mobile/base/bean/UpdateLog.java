package com.nhaowan.mobile.base.bean;

public class UpdateLog {
	private String appUrl;
	private String version;
	private String changeLog;
	//更新升级
	private int enforce;
	//
	private String picUrl;
	private String linkUrl;
	private String other;
	//更新海报  0 默认不更新
	private int forceUpdatePoster;
	
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getChangeLog() {
		return changeLog;
	}
	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
	public int getEnforce() {
		return enforce;
	}
	public void setEnforce(int enforce) {
		this.enforce = enforce;
	}
	public UpdateLog(String appUrl, String version, String changeLog,
			int enforce) {
		super();
		this.appUrl = appUrl;
		this.version = version;
		this.changeLog = changeLog;
		this.enforce = enforce;
	}
	public UpdateLog() {
		// TODO Auto-generated constructor stub
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	public int getForceUpdatePoster() {
		return forceUpdatePoster;
	}
	public void setForceUpdatePoster(int forceUpdatePoster) {
		this.forceUpdatePoster = forceUpdatePoster;
	}
}

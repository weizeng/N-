package com.nhaowan.mobile.base.response;

import com.leo.utils.Element;

public class UpdateAppResponse {
	
	@Element(value = "ret")
	private int ret;
	
	@Element(value = "changelog")
	private String changelog;
	
	@Element(value = "version")
	private String version;
	
	@Element(value = "appurl")
	private String appurl;
	
	@Element(value = "enforce")
	private int enforce;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	public int getEnforce() {
		return enforce;
	}

	public void setEnforce(int enforce) {
		this.enforce = enforce;
	}


	
	
}

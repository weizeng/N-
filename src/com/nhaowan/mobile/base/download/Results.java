package com.nhaowan.mobile.base.download;

public class Results {
	private int index;
	private String url;
	private Object object;
	public Results(int index, String url, Object object) {
		this.index=index;
		this.url=url;
		this.object=object;
		
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
}
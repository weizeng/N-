package com.nhaowan.mobile.base.utils;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ArticleBean implements Serializable {
	public final static int TYPE_DESPLAY_CONTENT = 0;
	public final static int TYPE_DESPLAY_PICTURE = 1;
	public static final int TEXT_STYLE_BOLD = 1;
	public static final int TEXT_STYLE_NORMAL = 0;
	private String text;
	private String picUrl;
	private String alt;
	private int textStyle;

	private ArrayList<String> needDownloadUrl = new ArrayList<String>();

	public ArticleBean(String contentFragment, String url, String alt) {
		super();
		this.text = contentFragment;
		this.picUrl = url;
		this.alt = alt;
	}

	public ArticleBean() {
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return picUrl;
	}

	public void setUrl(String url) {
		this.picUrl = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public ArrayList<String> getNeedDownloadUrl() {
		return needDownloadUrl;
	}

	public void setNeedDownloadUrl(ArrayList<String> needDownloadUrl) {
		this.needDownloadUrl = needDownloadUrl;
	}

	public int getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(int textStyle) {
		this.textStyle = textStyle;
	}

}

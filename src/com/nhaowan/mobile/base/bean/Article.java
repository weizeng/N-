package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.nhaowan.mobile.base.utils.ArticleBean;

public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String downUrl;
	private ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
	private String linkUrl;
	private String fileName;
	private String version;
	private ArrayList<String> videoSource = new ArrayList<String>();
	/**
	 * footer的类型 0：下载&& 网页链接 1： 推广 2： 投票 3： 相关文章
	 */
	private int footerStyle;

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public ArrayList<ArticleBean> getArticleList() {
		return articleList;
	}

	// public void setArticleList(ArrayList<ArticleFragment> articleList) {
	// this.articleList = articleList;
	// }
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<String> getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(ArrayList<String> videoSource) {
		this.videoSource = videoSource;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getFooterStyle() {
		return footerStyle;
	}

	public void setFooterStyle(int footerStyle) {
		this.footerStyle = footerStyle;
	}
}

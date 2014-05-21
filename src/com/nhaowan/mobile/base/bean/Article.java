package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.TextUtils;

import com.leo.utils.Element;
import com.nhaowan.mobile.base.utils.ArticleBean;
import com.nhaowan.mobile.base.utils.HtmlParseUtils;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Element(value = "title")
	private String title;

	@Element(value = "url")
	private String url;

	@Element(value = "downurl")
	private String downurl;

	@Element(value = "version")
	private String version;
	
	@Element(value = "videosource")
	private ArrayList<String> videoSource = new ArrayList<String>();
	
	@Element(value = "content")
	private String content;
	
	//string转换为各个文章片段
	private ArrayList<ArticleBean> articleList = new ArrayList<ArticleBean>();
	
	private String fileName;
	

	public ArrayList<ArticleBean> getArticleList() {
		return articleList;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownurl() {
		return downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public void setArticleList(ArrayList<ArticleBean> articleList) {
		this.articleList = articleList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean parseHtml() {
		if(!TextUtils.isEmpty(content)) {
			articleList.clear();
			articleList.addAll(HtmlParseUtils.parseHtml(content, null));
			return true;
		}
		return false;
	}

}

package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.text.TextUtils;

import com.leo.utils.Element;

public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Element(value = "username")
	private String username;

	@Element(value = "url")
	private String url;

	@Element(value = "user_avatar")
	private String avatar;

	@Element(value = "comment")
	private String[] comment;

	@Element(value = "id")
	protected int id;

	@Element(value = "title")
	protected String title;

	@Element(value = "create_time")
	protected long createTime;

	@Element(value = "thumb")
	protected String thumb;

	@Element(value = "description")
	protected String description;

	@Element(value = "catid")
	protected int catid;

	@Element(value = "subtitle")
	protected String subTitle;

	@Element(value = "zan")
	protected int zanCount;
	
	@Element(value = "cover")
	protected String cover;

	// 是否已经已读
	protected boolean readed;

	@Element(value = "views")
	protected int views;

	@Element(value = "icon")
	protected String icon;

	public BaseBean(int id, String title, long createTime, String thumb, String description, int catid,
			String subTitle, int zanCount, int views) {
		super();
		this.id = id;
		this.title = title;
		this.createTime = createTime;
		this.thumb = thumb;
		this.description = description;
		this.catid = catid;
		this.subTitle = subTitle;
		this.zanCount = zanCount;
		this.views = views;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String[] getComment() {
		return comment;
	}

	public void setComment(String[] comment) {
		this.comment = comment;
	}

	public int getViews() {
		return views;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public boolean isReaded() {
		return readed;
	}

	public int getCatid() {
		return catid;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public BaseBean() {
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		if (!TextUtils.isEmpty(title)) {
			try {
				return URLDecoder.decode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

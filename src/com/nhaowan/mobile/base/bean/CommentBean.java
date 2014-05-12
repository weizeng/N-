package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentBean implements Parcelable {

	private int id;
	private String content;
	private String createTime;
	private String thumb;
	private  int userId;
	private String username;
	private String ip;
	private int status;
	private int support;
	private int reply;
	private int direction;
	
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public CommentBean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param content
	 * @param createTime
	 * @param thumb
	 * @param needDownloadUrl
	 */
	public CommentBean(int id, String content, String thumb,String createTime 
			
			) {
		this.id = id;
		this.content = content;
		this.createTime = createTime;
		this.thumb = thumb;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String title) {
		this.content = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public void setNeedDownloadUrl(ArrayList<String> needDownloadUrl) {
		this.needDownloadUrl = needDownloadUrl;
	}
	public ArrayList<String> getNeedDownloadUrl() {
		return needDownloadUrl;
	}
	
	
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(content);
		dest.writeString(createTime);
		dest.writeString(thumb);
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserId() {
		return userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}

	public void setSupport(int support) {
		this.support = support;
	}
	public int getSupport() {
		return support;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}
	public int getReply() {
		return reply;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getDirection() {
		return direction;
	}

	public static final Parcelable.Creator<CommentBean> CREATOR = new Parcelable.Creator<CommentBean>() {

		@Override
		public CommentBean createFromParcel(Parcel source) {
			CommentBean p = new CommentBean();
			p.id = source.readInt();
			p.content = source.readString();
			p.createTime = source.readString();
			p.thumb = source.readString();
//			p.description = source.readString();
			return p;
		}

		@Override
		public CommentBean[] newArray(int size) {
			return new CommentBean[size];
		}
	};
	

}

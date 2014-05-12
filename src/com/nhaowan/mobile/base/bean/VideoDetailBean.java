package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoDetailBean extends BaseBean implements Parcelable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private long createTime;
	private String thumb;
	private String url;
	private String content;
	private int catId;
	private ArrayList<String> videoList  = new ArrayList<String>();
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public VideoDetailBean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param title
	 * @param createTime
	 * @param thumb
	 * @param needDownloadUrl
	 */
	public VideoDetailBean(int id, String title, String thumb,long createTime, String url 
			, String content
			, ArrayList<String> s) {
		this.id = id;
		this.title = title;
		this.createTime = createTime;
		this.thumb = thumb;
		this. url = url;
		this.content = content;
		this.videoList = s;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
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
		dest.writeString(title);
		dest.writeLong(createTime);
		dest.writeString(thumb);
		dest.writeString(url);
		dest.writeString(content);
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}

	public void setVideoList(ArrayList<String> videoList) {
		this.videoList = videoList;
	}
	public ArrayList<String> getVideoList() {
		return videoList;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getCatId() {
		return catId;
	}

	public static final Parcelable.Creator<VideoDetailBean> CREATOR = new Parcelable.Creator<VideoDetailBean>() {

		@Override
		public VideoDetailBean createFromParcel(Parcel source) {
			VideoDetailBean p = new VideoDetailBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.createTime = source.readLong();
			p.thumb = source.readString();
			p.url = source.readString();
			p.content = source.readString();
			return p;
		}

		@Override
		public VideoDetailBean[] newArray(int size) {
			return new VideoDetailBean[size];
		}
	};
	

}

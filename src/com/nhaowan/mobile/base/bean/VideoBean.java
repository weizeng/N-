package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoBean implements Parcelable {

	private int id;
	private String title;
	private String createTime;
	private String thumb;
	private int catid;
	private String description;
	
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public VideoBean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param title
	 * @param createTime
	 * @param thumb
	 * @param needDownloadUrl
	 */
	public VideoBean(int id, String title, String thumb,String createTime 
			, int catid,
			String destription) {
		this.id = id;
		this.title = title;
		this.createTime = createTime;
		this.thumb = thumb;
		this.catid=catid;
		this.description = destription;
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
		dest.writeString(title);
		dest.writeString(createTime);
		dest.writeString(thumb);
		dest.writeInt(catid);
		dest.writeString(description);
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}
	public int getCatid() {
		return catid;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	public static final Parcelable.Creator<VideoBean> CREATOR = new Parcelable.Creator<VideoBean>() {

		@Override
		public VideoBean createFromParcel(Parcel source) {
			VideoBean p = new VideoBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.createTime = source.readString();
			p.thumb = source.readString();
			p.catid = source.readInt();
			p.description = source.readString();
			return p;
		}

		@Override
		public VideoBean[] newArray(int size) {
			return new VideoBean[size];
		}
	};
	

}

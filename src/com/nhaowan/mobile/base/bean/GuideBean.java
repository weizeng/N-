package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class GuideBean implements Parcelable {

	private int id;
	private String title;
	private String createTime;
	private String thumb;
	private String description;
	private int zanCount;
	
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public GuideBean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param title
	 * @param createTime
	 * @param thumb
	 * @param needDownloadUrl
	 */
	public GuideBean(int id, String title, String thumb,String createTime ,
			String description
			) {
		this.id = id;
		this.title = title;
		this.createTime = createTime;
		this.thumb = thumb;
		this.description = description;
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
		dest.writeString(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}
	public int getZanCount() {
		return zanCount;
	}

	public static final Parcelable.Creator<GuideBean> CREATOR = new Parcelable.Creator<GuideBean>() {

		@Override
		public GuideBean createFromParcel(Parcel source) {
			GuideBean p = new GuideBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.createTime = source.readString();
			p.thumb = source.readString();
			p.description = source.readString();
			return p;
		}

		@Override
		public GuideBean[] newArray(int size) {
			return new GuideBean[size];
		}
	};
	

}

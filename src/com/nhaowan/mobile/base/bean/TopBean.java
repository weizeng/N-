package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class TopBean extends BaseBean implements Parcelable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();

	public TopBean() {
	}

	public TopBean(int id, String title, String thumb, int catid) {
		this.id = id;
		this.title = title;
		this.thumb = thumb;
		this.catid = catid;
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
		dest.writeString(thumb);
	}

	public static final Parcelable.Creator<TopBean> CREATOR = new Parcelable.Creator<TopBean>() {

		@Override
		public TopBean createFromParcel(Parcel source) {
			TopBean p = new TopBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.thumb = source.readString();
			return p;
		}

		@Override
		public TopBean[] newArray(int size) {
			return new TopBean[size];
		}
	};

}

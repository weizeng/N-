package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ZanBean implements Parcelable {
	int catid;
	int create_time;
	String description;
	int id;
	private ArrayList<String> needDownloadUrl = new ArrayList();
	String subtitle;
	String thumb;
	String title;
	int views;
	int zan;

	public int describeContents() {
		return 0;
	}

	public int getCatid() {
		return this.catid;
	}

	public int getCreate_time() {
		return this.create_time;
	}

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public ArrayList<String> getNeedDownloadUrl() {
		return this.needDownloadUrl;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public String getThumb() {
		return this.thumb;
	}

	public String getTitle() {
		return this.title;
	}

	public int getViews() {
		return this.views;
	}

	public int getZan() {
		return this.zan;
	}

	public void setCatid(int paramInt) {
		this.catid = paramInt;
	}

	public void setCreate_time(int paramInt) {
		this.create_time = paramInt;
	}

	public void setDescription(String paramString) {
		this.description = paramString;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setNeedDownloadUrl(ArrayList<String> paramArrayList) {
		this.needDownloadUrl = paramArrayList;
	}

	public void setSubtitle(String paramString) {
		this.subtitle = paramString;
	}

	public void setThumb(String paramString) {
		this.thumb = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public void setViews(int paramInt) {
		this.views = paramInt;
	}

	public void setZan(int paramInt) {
		this.zan = paramInt;
	}

	public void writeToParcel(Parcel paramParcel, int paramInt) {
	}
}
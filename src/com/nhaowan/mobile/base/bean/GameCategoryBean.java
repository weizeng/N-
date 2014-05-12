package com.nhaowan.mobile.base.bean;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class GameCategoryBean implements Parcelable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String catid;
	private String catname;

	public GameCategoryBean() {
	}

	public GameCategoryBean(String catid, String catname) {
		super();
		this.catid = catid;
		this.catname = catname;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(catid);
		dest.writeString(catname);
	}

	public static final Parcelable.Creator<GameCategoryBean> CREATOR = new Parcelable.Creator<GameCategoryBean>() {

		@Override
		public GameCategoryBean createFromParcel(Parcel source) {
			GameCategoryBean p = new GameCategoryBean();
			p.catid = source.readString();
			p.catname = source.readString();
			return p;
		}

		@Override
		public GameCategoryBean[] newArray(int size) {
			return new GameCategoryBean[size];
		}
	};

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}
}
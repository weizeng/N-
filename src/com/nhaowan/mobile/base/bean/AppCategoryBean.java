package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.leo.utils.Element;

public class AppCategoryBean implements Parcelable, Serializable {
	private static final long serialVersionUID = 1L;
	public final static String CAT_WEBVIEW = "404";

	@Element(value = "catid")
	private int catid;

	@Element(value = "catname")
	private String catname;

	@Element(value = "type")
	private String type;

	@Element(value = "style")
	private String style = "0";

	@Element(value = "url")
	private String url = "http://www.nhaowan.com";

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getCatname() {
		try {
			return !TextUtils.isEmpty(catname) ? URLDecoder.decode(catname, "UTF-8") : "未知";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "未知";
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(catid);
		dest.writeString(catname);
		dest.writeString(type);
		dest.writeString(style);
	}

	public static final Parcelable.Creator<AppCategoryBean> CREATOR = new Parcelable.Creator<AppCategoryBean>() {

		@Override
		public AppCategoryBean createFromParcel(Parcel source) {
			AppCategoryBean p = new AppCategoryBean();
			p.catid = source.readInt();
			p.catname = source.readString();
			p.type = source.readString();
			p.style = source.readString();
			return p;
		}

		@Override
		public AppCategoryBean[] newArray(int size) {
			return new AppCategoryBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

}

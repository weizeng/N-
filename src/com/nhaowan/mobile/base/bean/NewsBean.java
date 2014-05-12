package com.nhaowan.mobile.base.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class NewsBean extends BaseBean implements Parcelable {
	private static final long serialVersionUID = 1L;
	// 默认图
	public final static int STYLE_BMAP_LARGE = 0;
	// 左侧默认小图片
	public final static int STYLE_BMAP_SMALL = 1;
	// 无图
	public final static int STYLE_BMAP_NO = 2;
	// 推荐
	public final static int STYLE_BMAP_HOT = 3;

	private int displayStyle;
	
	private int tid;
	
	public NewsBean() {
	}

	private ArrayList<String> needDownloadUrl = new ArrayList<String>();

	public NewsBean(int id, String title, String thumb, long createTime, String description, int catid,
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

	public int getViews() {
		return views;
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
		return TextUtils.isEmpty(subTitle)?"":subTitle;
	}

	public void setCatid(int catid) {
		this.catid = catid;
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
		dest.writeString(description);
		dest.writeInt(zanCount);
		dest.writeInt(catid);
	}

	public int getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(int displayStyle) {
		this.displayStyle = displayStyle;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public static final Parcelable.Creator<NewsBean> CREATOR = new Parcelable.Creator<NewsBean>() {

		@Override
		public NewsBean createFromParcel(Parcel source) {
			NewsBean p = new NewsBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.createTime = source.readLong();
			p.thumb = source.readString();
			p.description = source.readString();
			p.zanCount = source.readInt();
			p.catid = source.readInt();
			return p;
		}

		@Override
		public NewsBean[] newArray(int size) {
			return new NewsBean[size];
		}
	};
}

package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.leo.utils.Element;

public class CommentTopBean extends BaseBean implements Parcelable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Element(value = "total")
	private int total;
	
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public CommentTopBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentTopBean(int id, String title, String thumb,
			int catid, int total) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.title = title;
		this.thumb = thumb;
		this.catid = catid;
		this.total = total;
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
//		dest.writeString(type);
		dest.writeInt(total);
	}


	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public static final Parcelable.Creator<CommentTopBean> CREATOR = new Parcelable.Creator<CommentTopBean>() {

		@Override
		public CommentTopBean createFromParcel(Parcel source) {
			CommentTopBean p = new CommentTopBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.thumb = source.readString();
//			p.type = source.readString();
			p.total = source.readInt();
			return p;
		}

		@Override
		public CommentTopBean[] newArray(int size) {
			return new CommentTopBean[size];
		}
	};
	

}

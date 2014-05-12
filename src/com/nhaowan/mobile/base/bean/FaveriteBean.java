package com.nhaowan.mobile.base.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class FaveriteBean extends BaseBean implements Parcelable {
//	final static String TYPE_VIDEO = "v_show";
//	final static String TYPE_DETAIL="detail";
//	private String type;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> needDownloadUrl = new ArrayList<String>();
	
	public FaveriteBean() {
		// TODO Auto-generated constructor stub
	}
	
	public FaveriteBean(int id, String title, String thumb, 
			String description, long createTime, int catid) {
		super();
		this.id = id;
		this.title = title;
		this.thumb = thumb;
//		this.type = type;
		this.description = description;
		this.createTime = createTime;
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
		dest.writeInt(catid);
		dest.writeString(description);
		dest.writeLong(createTime);
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
 
	public static final Parcelable.Creator<FaveriteBean> CREATOR = new Parcelable.Creator<FaveriteBean>() {

		@Override
		public FaveriteBean createFromParcel(Parcel source) {
			FaveriteBean p = new FaveriteBean();
			p.id = source.readInt();
			p.title = source.readString();
			p.thumb = source.readString();
			p.catid = source.readInt();
			p.description = source.readString();
			p.createTime = source.readLong();
			return p;
		}

		@Override
		public FaveriteBean[] newArray(int size) {
			return new FaveriteBean[size];
		}
	};
	

}

package com.nhaowan.mobile.base.download;

import java.lang.ref.WeakReference;

public class BaseViewHolder {
	public WeakReference<ResourceInfo> tag;

	public ResourceInfo getTag() {
		ResourceInfo resInfo=tag.get();
		return resInfo;
	}

	public void setTag(ResourceInfo tag) {
		this.tag = new WeakReference<ResourceInfo>(tag);
	}
	
}

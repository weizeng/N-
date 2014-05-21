package com.nhaowan.mobile.base.task;

import android.content.Context;

import com.leo.net.AsyncHttpManager;

public abstract class AbsProxyTask<T> implements IProxyTask<Object> {
	public Context mContext;
	public Status status = Status.FAILED;
	AbsProxyTask(Context mContext) {
		this.mContext = mContext;
	}
	
	boolean hasNetwork(Context mContext) {
		return AsyncHttpManager.getInstance().checkConnection(mContext);
	}
	
	public abstract void onComplete(T t);
	public abstract void onFailed(String error);
	public void onProgress(T t){};
}

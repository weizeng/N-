package com.nhaowan.mobile.base.task;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.IWAsyncHttpResponseHandler;
import com.haha.frame.utils.SharedPreferencesUtil;
import com.haha.frame.widget.IDataLoaderListener;
import com.haha.frame.widget.ImageLoader;
import com.nhaowan.mobile.base.response.HomeScreenResponse;
import com.nhaowan.mobile.base.task.IProxyTask.Status;
import com.nhaowan.mobile.base.utils.Contants;

public abstract class HomeScreenTask extends AbsProxyTask<HomeScreenResponse> {

	HomeScreenTask(Context mContext) {
		super(mContext);
	}

	@Override
	public com.nhaowan.mobile.base.task.IProxyTask.Status getStatus() {
		return status;
	}

	@Override
	public void onTaskStart() {
		status = Status.GOING;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", "screen");
		// 更新开始大图界面
		AsyncHttpManager.getInstance().get(mContext, "/app/home", params,
				Contants.SERVICE_ROOT + "/app/home",
				new IWAsyncHttpResponseHandler<HomeScreenResponse>(HomeScreenResponse.class) {

					@Override
					public void onSuccess(HomeScreenResponse t) {
						if (t != null && t.getRet() == 0) {
							// loadWelcomeImage(mContext, t.getPicUrl());
							if (t.getPicUrl() != null) {
								loadWelcomeImage(mContext, t.getPicUrl());
							}
							onComplete(t);
						}
					}
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						onFailed(content);
					}
				});
	}

	private void loadWelcomeImage(final Context mContext, String picUrl) {
		ImageLoader.getInstance().setOnLoadImageListener(mContext, picUrl, 0, 0, true,
				new IDataLoaderListener<Bitmap>() {

					@Override
					public void onPending() {
					}

					@Override
					public void onCompleted(String tag, Bitmap obj) {
						SharedPreferencesUtil.saveConfInfo(mContext, Contants.UPDATE_INIT_FILE,
								Contants.UPDATE_INIT_FILE_KEY, tag.hashCode() + "");
						// TODO
					}

					@Override
					public void onFailed(int errorCode, String tag) {
						HomeScreenTask.this.onFailed(tag);
					}

					@Override
					public void onProgress(String tag, String progress) {
					}
				});
	}
}

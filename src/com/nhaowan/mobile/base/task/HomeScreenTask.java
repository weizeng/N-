package com.nhaowan.mobile.base.task;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.leo.net.AsyncHttpManager;
import com.leo.net.IWAsyncHttpResponseHandler;
import com.leo.utils.PreferenceUtils;
import com.nhaowan.mobile.base.response.HomeScreenResponse;
import com.nhaowan.mobile.base.utils.Contants;
import com.nhaowan.mobile.base.utils.ImageManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public abstract class HomeScreenTask extends AbsProxyTask<String> {

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
					public void onTaskSuccess(final HomeScreenResponse t) {
						if (t != null && t.getRet() == 0) {
							if (t.getPicUrl() != null) {
								String fileKey = PreferenceUtils.getContactPreference(mContext,
										Contants.UPDATE_INIT_FILE, Contants.UPDATE_INIT_FILE_KEY);
								if (t.getPicUrl().equals(fileKey)) {
									onComplete(null);
								} else {
									ImageLoader.getInstance().loadImage(t.getPicUrl(),ImageManager.getDisplayImageOption(),
											new ImageLoadingListener() {

												@Override
												public void onLoadingStarted(String imageUri, View view) {

												}

												@Override
												public void onLoadingFailed(String imageUri, View view,
														FailReason failReason) {
												}

												@Override
												public void onLoadingComplete(String imageUri, View view,
														Bitmap loadedImage) {
													PreferenceUtils.saveConfInfo(mContext, Contants.UPDATE_INIT_FILE,
															Contants.UPDATE_INIT_FILE_KEY, t.getPicUrl() + "");
													onComplete(t.getPicUrl());
													
												}

												@Override
												public void onLoadingCancelled(String imageUri, View view) {
												}
											});
								}
							} else {
								onFailed("没有封面地址");
							}
						}
					}
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						onFailed(content);
					}
				});
		}
}

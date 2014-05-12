package com.nhaowan.mobile.base.task;

import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.IWAsyncHttpResponseHandler;
import com.haha.frame.utils.DeviceUtils;
import com.nhaowan.mobile.base.response.UpdateAppResponse;
import com.nhaowan.mobile.base.task.IProxyTask.Status;
import com.nhaowan.mobile.base.utils.Contants;

public abstract class VersionUpdateTask extends AbsProxyTask<UpdateAppResponse> {

	VersionUpdateTask(Context mContext) {
		super(mContext);
	}
	@Override
	public com.nhaowan.mobile.base.task.IProxyTask.Status getStatus() {
		return status;
	}
	@Override
	public void onTaskStart() {
		status = Status.GOING;
		int versionCode = 0;
		PackageInfo pi = DeviceUtils.getCurrentPacketInfo(mContext);
		if (pi != null) {
			versionCode = pi.versionCode;
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("version", versionCode+"");
		// 版本更新
		AsyncHttpManager.getInstance().get(mContext,"/app/app_update",params,
				Contants.SERVICE_ROOT + "/app/app_update",
				new IWAsyncHttpResponseHandler<UpdateAppResponse>(UpdateAppResponse.class) {

					@Override
					public void onSuccess(UpdateAppResponse t) {
						if (t != null && t.getRet() == 0) {
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

}

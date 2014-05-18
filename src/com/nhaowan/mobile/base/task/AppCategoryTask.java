package com.nhaowan.mobile.base.task;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.IWAsyncHttpResponseHandler;
import com.haha.frame.utils.DateUtil;
import com.haha.frame.utils.FileSerializable;
import com.haha.frame.utils.SharedPreferencesUtil;
import com.nhaowan.mobile.base.bean.AppCategoryBean;
import com.nhaowan.mobile.base.response.AppCategoryResponse;
import com.nhaowan.mobile.base.task.IProxyTask.Status;
import com.nhaowan.mobile.base.utils.Contants;

public abstract class AppCategoryTask extends AbsProxyTask<ArrayList<AppCategoryBean>> {
	private String TAG = AppCategoryTask.class.getSimpleName();

	ArrayList<AppCategoryBean> cbs;
	private boolean checkDataExperid;

	public AppCategoryTask(Context mContext, boolean checkDataExperid) {
		super(mContext);
		this.checkDataExperid = checkDataExperid;
	}

	@Override
	public com.nhaowan.mobile.base.task.IProxyTask.Status getStatus() {
		return status;
	}

	@Override
	public void onTaskStart() {
		status = Status.GOING;
		if (cbs == null) {
			cbs = new ArrayList<AppCategoryBean>();
		}
		Object obj = null;
		obj = getNativeAppCategory();
		if ((!checkDataExperid && cbs != null) || !AsyncHttpManager.getInstance().checkConnection(mContext)) {
			// fetcher.onFetcherSuccess(cbs);
			// return true;
			onComplete(cbs);
			return;
		}

		String timeStr = SharedPreferencesUtil.getContactPreference(mContext, Contants.UPDATE_INIT_FILE, ""
				+ Contants.UPDATE_INIT_CATEGORY_KEY);
		if (!checkDataExperid) {
			// 验证是否过期
			if ((cbs != null && cbs.size() > 0 && !TextUtils.isEmpty(timeStr) && !DateUtil.isDataExperid(
					Long.parseLong(timeStr), Contants.TIME_INTERVEL, mContext))) {
				onComplete(cbs);
			} else {
				fetchAppCatgory(mContext);
			}

		} else {
			if (cbs != null) {
				cbs.clear();
			} else {
				cbs = new ArrayList<AppCategoryBean>();
			}
			fetchAppCatgory(mContext);
		}
	}

	public ArrayList<AppCategoryBean> getNativeAppCategory() {
		try {
			cbs = (ArrayList<AppCategoryBean>) FileSerializable.readFromLocal(Contants.SERIAL_CATEGORY_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cbs;
	}

	private synchronized void fetchAppCatgory(final Context mContext) {
		AsyncHttpManager.getInstance().get(mContext, "/app/category", null,
				"http://www.nhaowan.com/app/category",
				new IWAsyncHttpResponseHandler<AppCategoryResponse>(AppCategoryResponse.class) {

					@Override
					public void onSuccess(AppCategoryResponse t) {
						if (t != null && t.getRet() == 0) {
							cbs.clear();
							cbs.addAll(t.getList());
							onComplete(cbs);
							SharedPreferencesUtil.saveConfInfo(mContext, Contants.UPDATE_INIT_FILE, ""
									+ Contants.UPDATE_INIT_CATEGORY_KEY, System.currentTimeMillis() + "");

							FileSerializable.serialize2Local(cbs, Contants.SERIAL_CATEGORY_FILE);
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						onFailed("网络请求出错啦");
					}
				});
	}

}

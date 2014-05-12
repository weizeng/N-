package com.nhaowan.mobile.base.task;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.AsyncHttpResponseHandler;
import com.haha.frame.utils.DateUtil;
import com.haha.frame.utils.FileSerializable;
import com.haha.frame.utils.JsonUtils;
import com.haha.frame.utils.Log;
import com.haha.frame.utils.SharedPreferencesUtil;
import com.nhaowan.mobile.base.bean.GameCategoryBean;
import com.nhaowan.mobile.base.task.IProxyTask.Status;
import com.nhaowan.mobile.base.utils.Contants;

public abstract class GameCategoryTask extends AbsProxyTask<ArrayList<GameCategoryBean>> {

	public GameCategoryTask(Context mContext) {
		super(mContext);
	}

	private String TAG = GameCategoryTask.class.getSimpleName();

	private ArrayList<GameCategoryBean> gcb = new ArrayList<GameCategoryBean>();

	@SuppressWarnings("unchecked")
	@Override
	public void onTaskStart() {
		status = Status.GOING;
		try {
			gcb = (ArrayList<GameCategoryBean>) FileSerializable.readFromLocal(Contants.SERIAL_CAT_MAP);
		} catch (Exception e1) {
		}

		String timeStr = SharedPreferencesUtil.getContactPreference(mContext, Contants.UPDATE_INIT_FILE, ""
				+ Contants.UPDATE_INIT_CATEGORY_GAME_KEY);
		if (gcb != null && !TextUtils.isEmpty(timeStr)
				&& !DateUtil.isDataExperid(Long.parseLong(timeStr), (long) (60 * 3 * 1000), mContext)) {
			// ignore
			onComplete(gcb);
		} else {
			if (gcb != null) {
				onProgress(gcb);
				gcb.clear();
			} else {
				gcb = new ArrayList<GameCategoryBean>();
			}
			fetchGameCategorgByHttp(mContext);
		}
	}

	@Override
	public com.nhaowan.mobile.base.task.IProxyTask.Status getStatus() {
		return status;
	}

	private void fetchGameCategorgByHttp(final Context mContext) {
		AsyncHttpManager.getInstance().get(mContext, "/app/catlist", null,
				Contants.SERVICE_ROOT + "/app/catlist", new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, String content) {
						fetchGameCategorySuccess(mContext, content);
					}
					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						onFailed(content);
					}
				});
	}

	private void fetchGameCategorySuccess(Context mContext, String content) {

		if (!TextUtils.isEmpty(content)) {
			try {
				Log.d(TAG, Contants.SERVICE_ROOT + "catlist" + ">>>>" + content);
				JSONObject jsonObject = new JSONObject(content);
				if (0 == jsonObject.getInt("ret")) {
					JSONObject js = JsonUtils.getJSONObject(jsonObject, "list");
					Iterator<?> keyIter = js.keys();
					String key;
					String value;
					gcb.clear();
					while (keyIter.hasNext()) {
						key = (String) keyIter.next();
						value = (String) js.get(key);
						GameCategoryBean gg = new GameCategoryBean(key, value);

						gcb.add(gg);
					}
					SharedPreferencesUtil.saveConfInfo(mContext, Contants.UPDATE_INIT_FILE, ""
							+ Contants.UPDATE_INIT_CATEGORY_GAME_KEY, System.currentTimeMillis() + "");
					boolean is = FileSerializable.serialize2Local(gcb, Contants.SERIAL_CAT_MAP);
					onComplete(gcb);
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}
		} else {
		}

	}

}

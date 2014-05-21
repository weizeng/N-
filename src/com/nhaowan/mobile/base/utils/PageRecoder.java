package com.nhaowan.mobile.base.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.text.TextUtils;

import com.leo.net.AsyncHttpManager;
import com.leo.net.AsyncHttpResponseHandler;
import com.leo.utils.FileSerializable;
import com.leo.utils.JsonUtils;
import com.leo.utils.Log;

public class PageRecoder {
	private final String TAG = "PageRecoder";
	static PageRecoder pageRecoder;
	private Context mContext;

	public static PageRecoder getInstance(Context mContext) {
		if (pageRecoder == null) {
			pageRecoder = new PageRecoder(mContext);
		}
		return pageRecoder;
	}

	private ArrayList<RecoderContent> recordList = new ArrayList<PageRecoder.RecoderContent>();

	public PageRecoder(final Context mContext) {
		this.mContext = mContext;
	}

	public static class RecoderContent {
		int catid;
		int id;
		int count = 1;

		public RecoderContent(int catid, int id) {
			super();
			this.catid = catid;
			this.id = id;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null) {
				RecoderContent rcTemp = (RecoderContent) o;
				if (rcTemp.catid == this.catid && rcTemp.id == this.id) {
					return true;
				}
			}
			return false;
		}

	}

	public void record(RecoderContent record) {
		if (recordList == null) {
			recordList = new ArrayList<PageRecoder.RecoderContent>();
		}
		boolean isAdd = false;
		for (int i = 0; i < recordList.size(); i++) {
			if (recordList.get(i).equals(record)) {
				recordList.get(i).count += 1;
				isAdd = true;
				break;
			}
		}
		if (!isAdd) {
			recordList.add(record);
		}
		Log.d(TAG, "添加到内存 recordList");
		if (recordList.size() % 3 == 0) {
			Log.d(TAG, "开始序列化到本地");
			// 每新增三条则序列化到本地
			serializable2Local(recordList);
		}
	}

	public String toArrayString(ArrayList<RecoderContent> records) {
		JSONArray jsonArry = new JSONArray();
		for (int i = 0; i < records.size(); i++) {
			try {
				jsonArry.put(JsonUtils.toJSONObject(records.get(i)));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return jsonArry.toString();
	}

	private void serializable2Local(ArrayList<RecoderContent> records) {
		if (records != null) {
			String data = toArrayString(records);
			boolean res = FileSerializable.serialize2Local(data, Contants.SERIAL_HITS);
			Log.d(TAG, "点击量数据序列化到本地：" + res + ",data:" + data);
		}
	}

	public void onStop() {

	}

	public void onDestory() {
		AsyncHttpManager.getInstance().cancelRequests(mContext, true);
	}

	public synchronized boolean onCommit() {
		Object obj = null;
		try {
			obj = FileSerializable.readFromLocal(Contants.SERIAL_HITS);
		} catch (Exception e1) {
		}
		if (obj != null) {
			String commitStr = (String) obj;
			Log.d(TAG, "commitStr>>>" + commitStr);
			// CommonUtils.showToast(mContext,commitStr);
			AsyncCommitHitsTask((String) obj);
			return true;
		} else if (recordList != null && recordList.size() > 0) {
			String commitStr = toArrayString(recordList);
			// CommonUtils.showToast(mContext,commitStr);
			Log.d(TAG, "commitStr>>>" + commitStr);
			AsyncCommitHitsTask(commitStr);
		}

		return false;
	}

	private synchronized void AsyncCommitHitsTask(String hits) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("list", hits);

		AsyncHttpManager.getInstance().post(mContext, "/app/hits", params, Contants.SERVICE_HITS, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (!TextUtils.isEmpty(content)) {
					// 提交后需要删除hits缓冲文件
					File file = new File(Contants.SERIAL_HITS);
					if (file != null && file.exists()) {
						file.delete();
					}
					recordList.clear();
				}
			}
		});
	}
}

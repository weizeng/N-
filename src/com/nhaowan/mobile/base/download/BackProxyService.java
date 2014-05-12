package com.nhaowan.mobile.base.download;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.AsyncHttpResponseHandler;
import com.haha.frame.utils.DeviceUtils;
import com.haha.frame.utils.FileUtils;
import com.haha.frame.utils.Log;
import com.haha.frame.utils.SharedPreferencesUtil;
import com.nhaowan.mobile.base.utils.Contants;

/**
 * 启动后开启一个后台服务，用来传输数据
 * 
 * @author leo
 * 
 */
public class BackProxyService extends IntentService {
	private static String TAG = "BackProxyService";

	public BackProxyService() {
		super("BackProxyService");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		List<PackageInfo> packageInfos = DeviceUtils.getAllApps(this);
		List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
		if (!AsyncHttpManager.getInstance().checkConnection(this)) {
			return;
		}
		try {
			JSONArray appsDataArray = new JSONArray();
			for (int i = 0; i < packageInfos.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				PackageInfo pi = packageInfos.get(i);
				jsonObject.put("packName", pi.packageName);
				jsonObject.put("versionName", pi.versionName);
				jsonObject.put("versionCode", pi.versionCode);
				jsonObject.put("appName", pi.applicationInfo.loadLabel(getPackageManager()));
				double size = FileUtils.getSize(new File(pi.applicationInfo.publicSourceDir), true);
				jsonObject.put("filesize", size + "MB");

				appsDataArray.put(jsonObject);
			}
			// 已安装软件信息
			nameValueList.add(new BasicNameValuePair("apps", appsDataArray.toString()));

			JSONObject deviceJson = new JSONObject();
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			deviceJson.put("model", android.os.Build.MODEL);
			deviceJson.put("device", android.os.Build.DEVICE);
			deviceJson.put("id", android.os.Build.ID);
			deviceJson.put("sdk", Build.VERSION.SDK);
			deviceJson.put("brand", Build.BRAND);
			deviceJson.put("cpu_abi", Build.CPU_ABI);
			deviceJson.put("manufacturer", Build.MANUFACTURER);

			deviceJson.put("release", android.os.Build.VERSION.RELEASE);
			deviceJson.put("deviceuid", DeviceUtils.getDeviceUUid(this));
			deviceJson.put("softversion", tm.getDeviceSoftwareVersion());

			deviceJson.put("appname", this.getApplicationInfo().loadLabel(this.getPackageManager()));
			try {
				deviceJson.put("appversion",
						this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			} catch (NameNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 系统基础信息
			nameValueList.add(new BasicNameValuePair("mobile", deviceJson.toString()));

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("mobile", deviceJson.toString());
			params.put("apps", appsDataArray.toString());

			String token = SharedPreferencesUtil.getContactPreference(this, Contants.UPDATE_INIT_FILE,
					Contants.UPDATE_INIT_USER_TOKEN);
			if (!TextUtils.isEmpty(token)) {
				nameValueList.add(new BasicNameValuePair("token", token));
			}

			AsyncHttpManager.getInstance().post(this, "/users/post_apps", params,
					Contants.SERVICE_POST_MOBILE, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers, String content) {
							super.onSuccess(statusCode, headers, content);
							Log.d(TAG, "<<<<<<" + content);
						}
					});
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public int onStartCommand(final Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
}

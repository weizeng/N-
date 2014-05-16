package com.nhaowan.mobile.ui;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.haha.frame.common.CommonUtils;
import com.haha.frame.core.BitmapCacheUtils;
import com.haha.frame.utils.SharedPreferencesUtil;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.response.UpdateAppResponse;
import com.nhaowan.mobile.base.task.NSysInitialProxy;
import com.nhaowan.mobile.base.task.NSysInitialProxy.INSConf;
import com.nhaowan.mobile.base.utils.Contants;

public class WelcomeActivity extends Activity {
	private String TAG = WelcomeActivity.class.getSimpleName();
	private boolean isSwitch2Main = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.welcome_layout);

		ImageView loadingView = (ImageView) findViewById(R.id.init_imageview);
		// 得到hashcode值
		String tag = SharedPreferencesUtil.getContactPreference(this, Contants.UPDATE_INIT_FILE,
				Contants.UPDATE_INIT_FILE_KEY);

		Bitmap bitmap = null;
		if (!TextUtils.isEmpty(tag)) {
			bitmap = BitmapCacheUtils.getCacheBitmapWithFile(tag,
					new File(Contants.PATH_SDCARD_IMAGES + tag), 0, 0);
		}
		if (bitmap != null) {
			loadingView.setImageBitmap(bitmap);
		}

		NSysInitialProxy.getInstance().initSysConf(this, new INSConf() {

			@Override
			public void onProgress(Object t) {
				if (t != null  && t instanceof UpdateAppResponse) {
					createNotifacation((UpdateAppResponse)t);
				}
			}

			@Override
			public void onFailed(String error) {

			}

			@Override
			public void onCompleteAll() {
				toMainOrActionActivity();
			}
		});

//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//
//			@Override
//			public void uncaughtException(Thread arg0, Throwable arg1) {
//				CommonUtils.showToast(WelcomeActivity.this, "Oh, 不给力，居然奔溃！");
//
//				StringWriter sw = new StringWriter();
//				PrintWriter pw = new PrintWriter(sw);
//				StatService.onEvent(WelcomeActivity.this, "panic", sw.toString(), 1);
//			}
//		});
	}

	protected void toMainOrActionActivity() {
		if (isSwitch2Main) {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
		} else {
//			Intent i = new Intent(InitActivity.this, ActionActivity.class);
//			startActivity(i);
//			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 创建notification
	 * 
	 * @param info
	 */
	private void createNotifacation(UpdateAppResponse info) {
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建一个Notification
		Notification notification = new Notification();
		// 设置显示在手机最上边的状态栏的图标
		notification.icon = R.drawable.ic_launcher;
		// 当当前的notification被放到状态栏上的时候，提示内容
		notification.tickerText = "N好玩-" + info.getVersion() + "有更新咯，请及时下载更新哈";
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		// 点击状态栏的图标出现的提示信息设置
		notification.setLatestEventInfo(this, "N好玩 提醒您", info.getVersion() + "有更新咯，请及时下载更新哈", null);
		manager.notify(0, notification);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}

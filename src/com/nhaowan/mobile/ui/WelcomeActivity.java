package com.nhaowan.mobile.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.baidu.mobstat.StatService;
import com.leo.utils.FileUtils;
import com.leo.utils.PreferenceUtils;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.response.UpdateAppResponse;
import com.nhaowan.mobile.base.task.NSysInitialProxy;
import com.nhaowan.mobile.base.task.NSysInitialProxy.INSConf;
import com.nhaowan.mobile.base.utils.Contants;
import com.nhaowan.mobile.base.utils.ImageManager;
import com.nhaowan.mobile.base.view.KenBurnsView;

public class WelcomeActivity extends Activity {
	private String TAG = WelcomeActivity.class.getSimpleName();
	private boolean isSwitch2Main = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_layout);

		init();
	}

	private void init() {
		final String welcome = Contants.PATH_SDCARD_SAVE_IMAGES+"welcome.jpg";
		if(!FileUtils.checkFileExists(welcome)){
			boolean rs = FileUtils.copyAssets(this,"images", Contants.PATH_SDCARD_SAVE_IMAGES);
		}
		
		final KenBurnsView loadingView = (KenBurnsView) findViewById(R.id.init_imageview);
		// 得到hashcode值
		String tag = PreferenceUtils.getContactPreference(this, Contants.UPDATE_INIT_FILE,
				Contants.UPDATE_INIT_FILE_KEY);

		if (!TextUtils.isEmpty(tag)) {
			 loadingView.setBitmaps("file://"+welcome, tag);
		}else {
			loadingView.setBitmaps("file://"+welcome);
		}

		NSysInitialProxy.getInstance().initSysConf(this, new INSConf() {

			@Override
			public void onProgress(Object t) {
				if (t != null) {
					if (t instanceof UpdateAppResponse) {
						UpdateAppResponse response = (UpdateAppResponse) t;
						if (!TextUtils.isEmpty(response.getAppurl())) {
							createNotifacation(response);
						}
					} else if (t instanceof String) {
						loadingView.setBitmaps((String)t, "file://"+welcome);
					}
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
	}

	protected void toMainOrActionActivity() {
		if (isSwitch2Main) {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
		} else {
			// Intent i = new Intent(InitActivity.this, ActionActivity.class);
			// startActivity(i);
			// finish();
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
		notification.tickerText = "请及时更新噢，我们有新版本啦：" + info.getVersion();
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

		Uri uri = Uri.parse(info.getAppurl());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		notification.contentIntent = createPendingResult(0, intent, 0);
		// 点击状态栏的图标出现的提示信息设置
		// notification.setLatestEventInfo(this, "N好玩 提醒您", info.getVersion() +
		// "有更新咯，请及时下载更新哈", null);
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

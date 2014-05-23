package com.nhaowan.mobile.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaApplication;
import com.leo.Constants;
import com.nhaowan.mobile.base.bean.User;
import com.nhaowan.mobile.base.utils.ImageManager;
import com.nhaowan.mobile.base.utils.bd.ThirdPlatform;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class WanApplication extends Application {
	@Override
	public void onCreate() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog()
//					.build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
//		}
		super.onCreate();
		initImageLoader(getApplicationContext());
		FrontiaApplication.initFrontiaApplication(getApplicationContext());
		Frontia.init(this.getApplicationContext(), ThirdPlatform.APIKEY);
		// 作为网络请求时 请求id
		Constants.initAppKey("89765f16cc764bcd8c5ef39337d7c250");
		// 作为网络请求时 请求token
		Constants.initToken(User.getInstance().getToken());
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release  app
				.defaultDisplayImageOptions(ImageManager.getDisplayImageOption())
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}

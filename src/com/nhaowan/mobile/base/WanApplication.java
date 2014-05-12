package com.nhaowan.mobile.base;

import android.app.Application;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaApplication;
import com.haha.frame.LibsContants;
import com.haha.frame.core.BitmapCacheUtils;
import com.haha.frame.widget.ImageLoader;
import com.nhaowan.mobile.base.bean.User;
import com.nhaowan.mobile.base.utils.Contants;
import com.nhaowan.mobile.base.utils.bd.FrontiaConf;

public class WanApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		new BitmapCacheUtils(this, Contants.PATH_SDCARD_IMAGES);
		ImageLoader.getInstance().init(getApplicationContext(), Contants.PATH_SDCARD_IMAGES);
//		FrontiaApplication.initFrontiaApplication(getApplicationContext());
//		Frontia.init(this.getApplicationContext(), FrontiaConf.APIKEY);
		// 作为网络请求时 请求id
		LibsContants.initAppKey("89765f16cc764bcd8c5ef39337d7c250");
		// 作为网络请求时 请求token
		LibsContants.initToken(User.getInstance().getToken());
	}
}

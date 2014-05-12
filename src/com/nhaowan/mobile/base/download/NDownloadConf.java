package com.nhaowan.mobile.base.download;

import android.os.Environment;

import com.nhaowan.mobile.base.utils.Contants;

public class NDownloadConf {
	public static String getPath(){
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
//			return MountTask.getSystemStoragePath()+"/.pps/";
			return "/data/data/"+"com.nhaowan.mobile"+"/files/";
		}else{
//			return Environment.getExternalStorageDirectory().getPath() + "/.pps/";
			return Contants.PATH_SDCARD_FILES;
		}
	}
	public static int ADD_UNDOWN = 0;//
	
	//正在等待
	public static final int USE_TYPE_WAIT	    = 0;
	//正在下载
	public static final int USE_TYPE_RUN		= 1;
	//暂停
	public static final int USE_TYPE_STOP 		= 2;
	//重新下载
	public static final int USE_TYPE_RERUN 		= 3;
	//安装  （下载完成）
	public static final int USE_TYPE_INSTALL	= 4;
	//打开软件（安装完成）
	public static final int USE_TYPE_OPEN 		= 5;
		
	
	public final static int TYPE_DOWNLOAD_START= 0;
	
	public final static int TYPE_DOWNLOAD_COMPLETE= 1;
	
	public final static int TYPE_DOWNLOAD_FAILED = 2;
	
	public final static int TYPE_DOWNLOAD_UPDATE = 3;
	
	public final static int TYPE_DOWNLOAD_DELETE = 5;
	
	public final static int TYPE_DOWNLOAD_UPDATEFILE = 6;
	
	public final static int TYPE_DOWNLOAD_RESTART = 7;
	
	public final static int TYPE_DOWNLOAD_INSTALL = 8;
	
	public final static int TYPE_DOWNLOAD_CONTINUE = 9;
	
	
	public final static String FILE_APK_EXTENSION=".apk";
	public final static String FILE_CONF_EXTENSION=".conf";
	public final static String FILE_TMP_EXTENSION=".tmp";
	public static final long serialVersionUID = -4524193626843473325L;
	
	
}	

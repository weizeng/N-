package com.nhaowan.mobile.base.utils;

public class Contants {
	public static final String app_id = "10001";
	public static final String app_key = "89765f16cc764bcd8c5ef39337d7c250";
	
	public static final String UPDATE_INIT_FILE = "UPDATE_INIT_FILE";
	public static final String UPDATE_INIT_FILE_KEY = "UPDATE_INIT_FILE_KEY";
	public static final String UPDATE_INIT_CATEGORY_KEY = "UPDATE_INIT_CATEGORY_KEY";
	public static final String UPDATE_INIT_CATEGORY_GAME_KEY = "UPDATE_INIT_CATEGORY_GAME_KEY";
	public static final String SHARE_PRE_AUTO = "AuTo";
	public static final String UPDATE_INIT_USER_TOKEN = "UPDATE_INIT_USER_TOKEN";

	public final static String PATH_SDCARD_ROOT = android.os.Environment.getExternalStorageDirectory()
			+ "/nhaowan/";
	public final static String PATH_SDCARD_FILES = PATH_SDCARD_ROOT + "files/";
	public final static String PATH_SDCARD_IMAGES = PATH_SDCARD_ROOT + "images/";
	public final static String PATH_SDCARD_SAVE_IMAGES = PATH_SDCARD_ROOT + "save/";

	public final static String SERVICE_ROOT = "http://www.nhaowan.com";

	public final static String SERVICE_POST_MOBILE = SERVICE_ROOT + "/users/post_apps";
	public final static String SERVICE_TOP = SERVICE_ROOT + "/app/top";
	public final static String SERVICE_CATLIST = SERVICE_ROOT + "/app/catlist";
	public final static String SERVICE_HITS = SERVICE_ROOT + "/app/hits";

	/** 序列化文件 */
	public static final String SERIAL_CATEGORY_FILE = PATH_SDCARD_FILES + "cate.iw";
	public static final String SERIAL_TOP_FILE = PATH_SDCARD_FILES + "top.iw";
	public static final String SERIAL_USER_FILE = PATH_SDCARD_FILES + "user.iw";
	public static final String SERIAL_CONTENT_FILE = PATH_SDCARD_FILES + "artic.iw";
	public static final String SERIAL_VIDEO_FILE = PATH_SDCARD_FILES + "video.iw";
	public static final String SERIAL_COMMENT_TOP_FILE = PATH_SDCARD_FILES + "commontop.iw";
	public static final String SERIAL_CAT_MAP = PATH_SDCARD_FILES + "cat.iw";
	public static final String SERIAL_HITS = PATH_SDCARD_FILES + "iw.hits";

	public static final String TYPE_FRAGMENT_HAS_TOP = "HAS_TOP";
	public static final String TYPE_FRAGMENT_WHICH = "FRAGMENT";
	public static final String REFRESH_INDEX_KEY = "REFRESH_INDEX";
	public static final String TYPE_FRAGMENT_STYLE = "STYLE";

	public static final int REFRESH_INDEX_VALUE = 0;
	public static final int SCALE_SIZE = 2;
	public static final long TIME_INTERVEL = 60 * 3 * 1000;
}

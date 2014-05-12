package com.nhaowan.mobile.base.download;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.haha.frame.utils.Log;

public class DownloadService extends IntentService {
	private static String TAG="DownloadService";
	private DownloadManager downloadManager;
	public DownloadService() {
		super("DownloadService");
	}
	public static void startService(Context mContext, String key,String url, String name,String category,String jsonObj) {
		Log.d(TAG,"Service start!");
		Intent intent = new Intent(mContext, DownloadService.class);
		intent.putExtra("KEY", key);
		intent.putExtra("URL", url);
		intent.putExtra("FILENAME", name);
		intent.putExtra("CATEGORY", category);
		intent.putExtra("JSONOBJ", jsonObj);
		mContext.startService(intent);
	}
	public static void stopService(){
		DownloadManager.closeAll();
	}
	public static void stopService(Context mContext){
		DownloadManager.closeAll();
		Intent intent = new Intent(mContext, DownloadService.class);
		mContext.stopService(intent);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(final Intent intent) {
		Log.d(TAG,"onHandleIntent!");
		Log.d(TAG,"reveive download task:"+intent.getStringExtra("URL"));
		String category=intent.getStringExtra("CATEGORY");	
		if(!TextUtils.isEmpty(category)){
			downloadManager=DownloadManager.getInstace(this, category);
			if(downloadManager.getResInfoByKey(intent.getStringExtra("KEY"))==null){
				new AsyncTask<Void,Void,ResourceInfo>(){
					@Override
					protected ResourceInfo doInBackground(Void... params) {
						return createResourceInfo(intent.getStringExtra("KEY"), 
								intent.getStringExtra("URL"), 
								intent.getStringExtra("CATEGORY"),
								intent.getStringExtra("FILENAME"),
								intent.getStringExtra("JSONOBJ")
								);
					}
					@Override
					protected void onPostExecute(ResourceInfo result) {
						downloadManager.add(result);
					}
				}.execute();
			}
		}else{
			Log.d(TAG,"category is not null!");
		}
	}
	public int onStartCommand(final Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}
	
	private ResourceInfo createResourceInfo(String key,String url,String category,String fileName,String jsonObj){
		String fileNameShort=fileName.substring(0, fileName.lastIndexOf(".")==-1?fileName.length():fileName.lastIndexOf("."));
		String localFileRoot = NDownloadConf.getPath() +category+"/"+ fileNameShort;
		ResourceInfo resourceInfo = new ResourceInfo(key,url, localFileRoot, fileNameShort,jsonObj);
		return resourceInfo;
	}
}

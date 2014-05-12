package com.nhaowan.mobile.base.download;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.haha.frame.utils.Log;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.download.PoolManager.Pool;

public class DownloadManager{
	private final static String TAG="DownloadManager";
	protected static ConcurrentHashMap<String,DownloadManager> managerMap=null;
	private ArrayList<ResourceInfo> downloadRes=new ArrayList<ResourceInfo>();
	private ArrayList<WeakReference<DownloadStatusListener>> listeners=new  ArrayList<WeakReference<DownloadStatusListener>>();
//	private ArrayList<DownloadStatusListener> listeners=new  ArrayList<DownloadStatusListener>();
	private InternalHandler mHandler;
	private String mName;
	public Pool mThreadPool;
	private boolean isStopUi=false;
	private boolean isOffline=false;
	private static Context mContxt;
	
	private DownloadManager(String name){	
		this.mHandler=new InternalHandler(this);
		this.mName=name;
		this.mThreadPool=PoolManager.getPool(name);
		this.isStopUi=false;
	}
	public static synchronized  DownloadManager getInstace(Context context, String category){
		DownloadManager downloadManager=null;
		if(managerMap==null){
			managerMap=new ConcurrentHashMap<String,DownloadManager>();
		}
		if(managerMap.containsKey(category)){
			downloadManager=managerMap.get(category);
		}else{
			mContxt = context;
			downloadManager=new DownloadManager(category); 
			downloadManager.initOldDownloadRes(new File(NDownloadConf.getPath()+"/"+category));
			managerMap.put(category, downloadManager);
		}
		return downloadManager;
	}
	private  void initOldDownloadRes(File file){
		if(!file.exists()){
			file.mkdirs();
			return;
		}
		//遍历下载目录
		List<File> fileList=CommonUtils.tree(file,null);
		ResourceInfo resInfo=null;
		for (int i = 0; i < fileList.size(); i++) {   
			resInfo =ResourceInfo.initFromFile(fileList.get(i).getAbsolutePath());
			if(null!=resInfo){
				recoveryResInfo(resInfo);
				add(resInfo);
			}
		}
	}
	/**
	 * @hide
	 * @param resInfo
	 */
	private void recoveryResInfo(ResourceInfo resInfo){
		//纠正中断异常  等未正常退出状态
		if(resInfo.getFileLength()!=resInfo.getCompleteSize()||resInfo.getFileLength()==0){
			resInfo.setStatus(NDownloadConf.USE_TYPE_STOP);
			resInfo.updateConfFile();
		}
	}
	public void requestDownloadStatusListener(DownloadStatusListener downloadStatusListener){
		if(null==downloadStatusListener)
			throw new IllegalArgumentException("downloadStatusListener not null");
		DownloadStatusListener mListener=null;
		boolean isExist=false;
		for(int i=0;i<listeners.size();i++){
			mListener=listeners.get(i).get();
			if(downloadStatusListener.equals(mListener)){
				isExist=true;
				break;
			}
		}
		if(!isExist)listeners.add(new WeakReference<DownloadStatusListener>(downloadStatusListener));
	}
	public void removeDownloadStatusListener(DownloadStatusListener downloadStatusListener){
		if(null==downloadStatusListener)
			throw new IllegalArgumentException("downloadStatusListener not null");
		DownloadStatusListener mListener=null;
		for(int i=0;i<listeners.size();i++){
			mListener=listeners.get(i).get();
			if(downloadStatusListener.equals(mListener)){
				listeners.remove(i);
				break;
			}
		}
	}
//	public void requestDownloadStatusListener(DownloadStatusListener downloadStatusListener){
//		if(null==downloadStatusListener)
//			throw new IllegalArgumentException("downloadStatusListener not null");
//		DownloadStatusListener mListener=null;
//		boolean isExist=false;
//		for(int i=0;i<listeners.size();i++){
//			if(downloadStatusListener.equals(listeners.get(i))){
//				isExist=true;
//				break;
//			}
//		}
//		if(!isExist)listeners.add(downloadStatusListener);
//	}
//	public void removeDownloadStatusListener(DownloadStatusListener downloadStatusListener){
//		if(null==downloadStatusListener)
//			throw new IllegalArgumentException("downloadStatusListener not null");
//		DownloadStatusListener mListener=null;
//		for(int i=0;i<listeners.size();i++){
//			if(downloadStatusListener.equals(listeners.get(i))){
//				listeners.remove(i);
//				break;
//			}
//		}
//	}
	
	public ArrayList<ResourceInfo> getAllResourceList(){
		return downloadRes;
	}
	public synchronized ArrayList<ResourceInfo> getResourceList(boolean isComplete) {
		ArrayList<ResourceInfo> resList=new ArrayList<ResourceInfo>();
		ResourceInfo resInfo=null;
		for(int i=0;i<downloadRes.size();i++){
			resInfo=downloadRes.get(i);
			if(isComplete){
				//if(downloadRes.get(i).getProgress()==100)resList.add(downloadRes.get(i)); 
				if(resInfo.getStatus()>=NDownloadConf.USE_TYPE_INSTALL)resList.add(downloadRes.get(i)); 
			}else{
				//if(downloadRes.get(i).getProgress()!=100)resList.add(downloadRes.get(i)); 
				if(resInfo.getStatus()<NDownloadConf.USE_TYPE_INSTALL)resList.add(downloadRes.get(i)); 
			}
		}
		return resList;
	}
	public synchronized List<ResourceInfo> getResourceList(int status) {
		List<ResourceInfo> resList=new ArrayList<ResourceInfo>();
		for(int i=0;i<downloadRes.size();i++){
			if(status==downloadRes.get(i).getStatus())
			resList.add(downloadRes.get(i)); 
			
		}
		return resList;
	}

	public  synchronized void add(ResourceInfo resourceInfo){
		if(downloadRes.contains(resourceInfo)){
			return ;
		}
		/**
		 *  当前状态为
		 *   	1)开始下载
		 *  	2) 重下
		 *		3)断点续传
		 *   则开始任务
		 */
		DownloadTask downloadTask=new DownloadTask(mName,mHandler);
		if(resourceInfo.getStatus() == NDownloadConf.USE_TYPE_WAIT){
			downloadTask.start(resourceInfo);
		}else{
			downloadTask.resourceInfo=resourceInfo;
		}
		resourceInfo.setDownloadTask(downloadTask);
		synchronized(downloadRes){
			downloadRes.add(resourceInfo);
		}
		downloadTask.sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
		
	}
//	/**
//	 * @hide
//	 * @deprecated
//	 * @param url
//	 * @return
//	 */
//	public ResourceInfo getResInfo(String url){
//		if (null == url||"".equals(url)) {
//			throw new IllegalArgumentException("url==null");
//		}
//		for(int i=0;i<downloadRes.size();i++){
//			if(downloadRes.get(i).getUrl().equals(url)){
//				return downloadRes.get(i);
//			}
//		}
//		return null;
//	}
	public ResourceInfo getResInfoByKey(String key){
		if (null == key||"".equals(key)) {
			throw new IllegalArgumentException("key==null");
		}
		for(int i=0;i<downloadRes.size();i++){
			if(downloadRes.get(i).getKey().equals(key)){
				return downloadRes.get(i);
			}
		}
		return null;
	}
	public void recoverAll() {
		List<ResourceInfo> resList=getResourceList(false);
		ResourceInfo resInfo=null;
		for(int i=0;i<resList.size();i++){
			resInfo=resList.get(i);
			if(resInfo.getStatus()==NDownloadConf.USE_TYPE_RERUN){
				resume(resInfo);
			}
		}
	}
	public  void interrupt(ResourceInfo resourceInfo) {
		if(downloadRes.contains(resourceInfo)){
			DownloadTask downloadTask=resourceInfo.getDownloadTask();
			downloadTask.interrupt();
		}
	}
	public  void pause(ResourceInfo resourceInfo) {
		if(downloadRes.contains(resourceInfo)){
			DownloadTask downloadTask=resourceInfo.getDownloadTask();
			downloadTask.pause();
		}
	}
	public  void resume(ResourceInfo resourceInfo) {
		if(downloadRes.contains(resourceInfo)){
			DownloadTask downloadTask=resourceInfo.getDownloadTask();
			if(downloadTask.index<0){
				Message msg=new Message();
				msg.what=NDownloadConf.TYPE_DOWNLOAD_CONTINUE;
				msg.obj=resourceInfo;
				_handleMessage(msg);
			}
			downloadTask.resume(resourceInfo);
		}
	}
	public void restart(ResourceInfo resourceInfo){
		if(downloadRes.contains(resourceInfo)){
			DownloadTask downloadTask=resourceInfo.getDownloadTask();
			downloadTask.restart(resourceInfo);
		}
	}
	public  void remove(ResourceInfo resourceInfo) {
		synchronized(downloadRes){
			if(downloadRes.contains(resourceInfo)){
				DownloadTask downloadTask=resourceInfo.getDownloadTask();
				downloadTask.remove(resourceInfo);
				downloadRes.remove(resourceInfo);
			}
		}
	}
	public static void recoverAllAllDownloadManager() {
		if(null==managerMap)return;
		Enumeration<DownloadManager> en = managerMap.elements();
		while (en.hasMoreElements()) {
			DownloadManager manager=en.nextElement();
			manager.recoverAll();
			manager.isOffline=false;
		}
	}
	
	public static void stopAllDownloadManager() {
		// do nothing
		if(null==managerMap)return;
		Enumeration<DownloadManager> en = managerMap.elements();
		while (en.hasMoreElements()) {
			DownloadManager manager=en.nextElement();
			List<ResourceInfo> resList=manager.getResourceList(false);
			ResourceInfo resInfo=null;
			for(int i=0;i<resList.size();i++){
				resInfo=resList.get(i);
				if(resInfo.getStatus()<NDownloadConf.USE_TYPE_STOP){
					manager.interrupt(resInfo);
				}
			}
			manager.isOffline=true;
//			DownloadStatusListener listener=null;
//			for(int i=0;i<manager.listeners.size();i++){
//				listener=manager.listeners.get(i).get();
//				if(listener==null){
//					manager.listeners.remove(i);
//				}
//			}
		}
	}
	public static void closeAll() {
		if(null==managerMap)return;
		Enumeration<DownloadManager> en = managerMap.elements();
		while (en.hasMoreElements()) {
			en.nextElement().close();
		}
		managerMap.clear();
		managerMap=null;
		PoolManager.clear();
	}
	public void close() {
		//close listener
		if(listeners!=null)
			listeners.clear();
		//stop update ui
		isStopUi=true;
		synchronized(downloadRes){
			DownloadTask downloadTask=null;
			for(int i=0;i<downloadRes.size();i++){
				downloadTask=downloadRes.get(i).getDownloadTask();
				if(downloadTask!=null)
					downloadTask.pause();
				updateUnloadWhenClosed(downloadRes.get(i));
			}
		}	
		downloadRes.clear();
		//close pool
		mThreadPool.close();
		
	}
	public boolean isClose(){
		return mThreadPool.isThreadPoolClose();
	}
	public static boolean isAllClose() {
		if(null==managerMap)return true;
		boolean isClose=true;
		Enumeration<DownloadManager> en = managerMap.elements();
		while (en.hasMoreElements()) {
			isClose=isClose&&en.nextElement().isClose();
		}
		return isClose;
	}
	public void updateUnloadWhenClosed(ResourceInfo resourceInfo) {
		if(resourceInfo.getFileLength()==0||resourceInfo.getCompleteSize() != resourceInfo.getFileLength()){
			resourceInfo.setStatus(NDownloadConf.USE_TYPE_STOP);
			resourceInfo.setProgress((int) (((resourceInfo.getCompleteSize())/(float)resourceInfo.getFileLength()) *100));
			resourceInfo.updateConfFile();
		}
	}
	
	protected void _handleMessage(Message msg) {
		if(null==managerMap||isStopUi|| null==msg.obj)return;
		ResourceInfo resourceInfo=(ResourceInfo) msg.obj;
		//replece(resourceInfo);
		switch (msg.what) {
			case NDownloadConf.TYPE_DOWNLOAD_UPDATEFILE:
				resourceInfo.updateConfFile();
				return;
			default:
				notifyListener(resourceInfo,msg.what);
				break;
		}
		if(msg.what==NDownloadConf.TYPE_DOWNLOAD_COMPLETE&&resourceInfo.getStatus()==NDownloadConf.USE_TYPE_INSTALL){
			creatNotifacation(resourceInfo);
			install(resourceInfo);
			//Statistic
		 
		}else if(msg.what==NDownloadConf.TYPE_DOWNLOAD_CONTINUE){
			//Statistic
			 
		}
	}
	public void notifyListener(ResourceInfo resourceInfo,int type){
		DownloadStatusListener mListener=null;
		if (isUpdateUI(resourceInfo)) {
			for (int i = 0; i < listeners.size(); i++) {
				if (null != listeners.get(i).get()) {
					mListener=listeners.get(i).get();
					if(null!=mListener)mListener.onUpdate(resourceInfo,type);
				}
//				if (null != listeners.get(i)) {
//					mListener=listeners.get(i);
//					if(null!=mListener)mListener.onUpdate(resourceInfo,type);
//				}
			}
		}
	}
	private boolean isUpdateUI(ResourceInfo resourceInfo){
	   if(listeners != null &&listeners.size()>0 && resourceInfo != null){
		   return true;
	   }
	   return false;
	}
	/**
	 * @hide
     * @deprecated
	 * @param resourceInfo
	 */
	private void replece(ResourceInfo resourceInfo) {
		synchronized(downloadRes){
			try{
				if(downloadRes.contains(resourceInfo)){
					int index =downloadRes.indexOf(resourceInfo);
					downloadRes.remove(index);
					downloadRes.add(index, resourceInfo);
				}
			}catch(Exception e){
				Log.e(TAG, downloadRes+"\n="+resourceInfo);
			}
			
		}
	}
	final static class InternalHandler extends Handler {
		private final WeakReference<DownloadManager> mDownloadManager;
		public InternalHandler(DownloadManager downloadManager) {
			mDownloadManager = new WeakReference<DownloadManager>(downloadManager);
		}

		public void handleMessage(Message msg) {
		   DownloadManager downloadManager = mDownloadManager.get();
		   if (downloadManager != null) {
			   downloadManager._handleMessage(msg);
		   }
       }
	}
	private void install(ResourceInfo resInfo){
//		if(PPStvApp.getPPSInstance().isOnPlayingState())return;
		File file = new File(resInfo.getSourceFile());
		if (file.exists()) {
			Intent intent = new Intent(Intent.ACTION_VIEW);   
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.setDataAndType(Uri.parse("file://"  
		            + resInfo.getSourceFile()),   
		            "application/vnd.android.package-archive");   
		    mContxt.startActivity(intent);
		} else {
			DownloadManager.getInstace(mContxt, "game").remove(
					resInfo);
			DownloadManager
					.getInstace(mContxt,"game")
					.notifyListener(
							resInfo,
							NDownloadConf.TYPE_DOWNLOAD_DELETE);
//			Toast.makeText(PPStvApp.getPPSInstance(),
//					R.string.game_install_failed_redownload,
//					Toast.LENGTH_LONG).show();
		}
	}
	/**
	 * 创建notification
	 * 
	 * @param info
	 */
	private void creatNotifacation(ResourceInfo info) {
		NotificationManager manager = (NotificationManager) mContxt.getSystemService(Context.NOTIFICATION_SERVICE);
		// 创建一个Notification
		Notification notification = new Notification();
		// 设置显示在手机最上边的状态栏的图标
		notification.icon = R.drawable.ic_launcher;
		// 当当前的notification被放到状态栏上的时候，提示内容
		notification.tickerText = "N好玩提醒您，" + info.getFileName() + "已下载完成！";
		// 添加声音提示
		notification.defaults = Notification.DEFAULT_SOUND;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		String apkPath = info.getSourceFile();
		if (TextUtils.isEmpty(apkPath)) {
//			OtherUtils.AlertMessageInBottom("没有指定apk路径");
			com.haha.frame.common.CommonUtils.showToast(mContxt, "没有指定apk路径");
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkPath),
				"application/vnd.android.package-archive");
		PendingIntent pendingIntent = PendingIntent.getActivity(
				mContxt, 0, intent, 0);
		// 点击状态栏的图标出现的提示信息设置
		notification.setLatestEventInfo(mContxt, "N好玩提醒您",
				"点击安装：" + info.getFileName(), pendingIntent);
		manager.notify(0, notification);
	}
}
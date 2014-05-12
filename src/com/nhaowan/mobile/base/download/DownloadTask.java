package com.nhaowan.mobile.base.download;

import java.io.File;

import android.os.Message;

import com.nhaowan.mobile.base.download.DownloadManager.InternalHandler;

public class DownloadTask extends BaseDownloadUtils{
	private InternalHandler mHandler;
	public DownloadTask(String category,InternalHandler handler){
		super(category);
		mHandler=handler;
	}
	protected void start(ResourceInfo resourceInfo){
		super.start();
		futureTasks.add(executorService.submit(new RealDownTask(resourceInfo,futureTasks.size())));
		doDownloadStart(resourceInfo);
	}
	protected void restart(ResourceInfo resourceInfo) {
		synchronized(resourceInfo){
			resourceInfo.reset();
			sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATEFILE, resourceInfo);
		}
		if(futureTasks.size()>0&&index>=0)
		futureTasks.get(index).cancel(true);
		super.start();
		resourceInfo.setStatus(NDownloadConf.USE_TYPE_WAIT);
		futureTasks.add(executorService.submit(new RealDownTask(resourceInfo,futureTasks.size())));
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_RESTART, resourceInfo);
	}
	protected void resume(ResourceInfo resourceInfo) {
		super.start();
		resourceInfo.setStatus(NDownloadConf.USE_TYPE_WAIT);
		futureTasks.add(executorService.submit(new RealDownTask(resourceInfo,futureTasks.size())));
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
	}
	protected void pause() {
		super.stop();
		if(futureTasks.size()>0&&index>=0)
			futureTasks.get(index).cancel(true);
		resourceInfo.setStatus(NDownloadConf.USE_TYPE_STOP);
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
	}
	protected void interrupt() {
		super.stop();
		if(futureTasks.size()>0&&index>=0)
			futureTasks.get(index).cancel(true);
		resourceInfo.setStatus(NDownloadConf.USE_TYPE_RERUN);
		resourceInfo.setException("interrupt");
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
	}
	protected void remove(ResourceInfo resourceInfo) {
		super.stop();
		if(futureTasks.size()>0&&index>=0)
			futureTasks.get(index).cancel(true);
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_DELETE, resourceInfo);
		new DeleteFileThread(resourceInfo).start();
	}

	@Override
	public void doDownloadStart(ResourceInfo resourceInfo) {
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_START, resourceInfo);
	}
	@Override
	public void doDownloadUpdateView(ResourceInfo resourceInfo) {
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
	}

	@Override
	public void doDownloadUpdateFile(ResourceInfo resourceInfo) {
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATEFILE, resourceInfo);
	}

	@Override
	public void doDownloadComplete(ResourceInfo resourceInfo) {
		mHandler.removeMessages(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
		if(resourceInfo.getCompleteSize()==resourceInfo.getFileLength()&&resourceInfo.getFileLength()!=0){
			synchronized(resourceInfo){
				resourceInfo.setSpeed(0);
				resourceInfo.setProgress(100);
				resourceInfo.setStatus(NDownloadConf.USE_TYPE_INSTALL);
			}
			sendMessage(NDownloadConf.TYPE_DOWNLOAD_COMPLETE, resourceInfo);
		}else{
			synchronized(resourceInfo){
				resourceInfo.setSpeed(0);
				resourceInfo.setStatus(NDownloadConf.USE_TYPE_STOP);
			}
			sendMessage(NDownloadConf.TYPE_DOWNLOAD_UPDATE, resourceInfo);
		}
	}
	@Override
	public void doDownloadFailed(ResourceInfo resourceInfo) {
		resourceInfo.setSpeed(0);
		resourceInfo.setStatus(NDownloadConf.USE_TYPE_RERUN);
		resourceInfo.setException("下载失败,请检查网络连接/资源是否存在");
		sendMessage(NDownloadConf.TYPE_DOWNLOAD_FAILED, resourceInfo);
	}
	@Override
	public void doDownloadContinue(ResourceInfo resourceInfo) {
		//sendMessage(PPSConf.TYPE_DOWNLOAD_CONTINUE, resourceInfo);
	}
	public void sendMessage(int what,Object obj){
		  Message msg =mHandler.obtainMessage(what);
		  msg.obj = obj;
		  msg.sendToTarget();
	}
	class DeleteFileThread extends Thread{
		ResourceInfo resInfo;
		DeleteFileThread(ResourceInfo resInfo){
			this.resInfo=resInfo;
		}
		public void run(){
			File file=null;
			try {	
				file=new File(resInfo.getSourceFile());
				if(file!=null && file.exists()){
					file.delete();
				}
				file=new File(resInfo.getConfFile());
				if(file!=null && file.exists()){
					file.delete();
				}
				file=new File(resInfo.getLocalPath());
				if(file!=null && file.exists()){
					file.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package com.nhaowan.mobile.base.download;
/**
 * base Resouce
 * @author weixuewu
 *
 */
public abstract class BaseResource {
	abstract void setUrl(String url);
	abstract String getUrl();
	abstract void setFileName(String fileName);
	abstract String getFileName();
	abstract long getStartPos();
	abstract void setStartPos(long startPos);
	abstract long getEndPos();
	abstract void setEndPos(long endPos);
	abstract long getCompleteSize();
	abstract void setCompleteSize(long completeSize);
	abstract long getFileLength();
	abstract void setFileLength(long fileLength);
	abstract long getSpeed();
	abstract void setSpeed(long speed);
	abstract void setStatus(int status);
	abstract int getStatus();
	abstract int getProgress();
	abstract void setProgress(int progress);
	abstract String getException();
	abstract void setException(String exception);
	abstract String getLocalPath();
	abstract void setLocalPath(String localPath);
	abstract String getSourceFile();
	abstract void setSourceFile(String sourceFile);
	abstract String getConfFile();
	abstract void setConfFile(String confFile);
	
	abstract void initFile();
	abstract void initFromFile(String filePath);
	abstract void deleteFiles();
	abstract void updateConfFile();
	abstract void reset();
	
	abstract DownloadTask getDownloadTask();
	abstract void setDownloadTask(DownloadTask downloadTask);
}

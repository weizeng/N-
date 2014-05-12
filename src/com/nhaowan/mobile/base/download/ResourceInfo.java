package com.nhaowan.mobile.base.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.haha.frame.utils.Log;
import com.haha.frame.utils.StringUtils;




public class ResourceInfo implements Serializable{
	/**
	 * 
	 */
	protected static final long serialVersionUID = NDownloadConf.serialVersionUID;
	
	//开始偏移
	protected long startPos;
	//结束偏移
	protected long endPos;
	//已完成长度
	protected long completeSize;
	//文件长度
	protected long fileLength;
	//进度
	protected int progress;
	//速度 b/s
	protected long speed;
	//开始,暂停,安装,运行
	protected int status;
	//下载的URL
	protected String url;
	//下载的文件名
	protected String fileName;
	//本地文件更目录
	protected String localPath;
	//异常说明
	protected String exception;
	//源文件地址
	protected String sourceFile;
	//配置地址
	protected String confFile;
	//下载对象 json 字符串
	protected String  object;
	//下载任务
	protected transient DownloadTask downloadTask;
	//下载对象唯一标示
	protected String  key;
	//视图代理
	protected transient HashMap<Object,BaseViewHolder> viewHolders=new HashMap<Object,BaseViewHolder>();
	public ResourceInfo(String key,String url,String localPath,String fileName,String object) {
		this.key=key;
		this.url=url;
		this.fileName=fileName;
		this.localPath=localPath;
		this.object=object;
		initFile();
	}
	public ResourceInfo(){
	}
	public void initFile() {
		this.sourceFile=localPath+"/"+fileName;
		this.confFile=localPath+"/"+fileName+NDownloadConf.FILE_CONF_EXTENSION;
		initFile(sourceFile);
		initFile(confFile);
		updateConfFile();
	}
	public BaseViewHolder getViewHolder(Object obj) {
		return viewHolders.get(obj);
	}

	public void setViewHolder(Object obj,BaseViewHolder viewHolder) {
		this.viewHolders.put(obj, viewHolder);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStartPos() {
		return startPos;
	}

	public void setStartPos(long startPos) {
		this.startPos = startPos;
	}

	public long getEndPos() {
		return endPos;
	}

	public void setEndPos(long endPos) {
		this.endPos = endPos;
	}

	public long getCompleteSize() {
		return completeSize;
	}

	public void setCompleteSize(long completeSize) {
		this.completeSize = completeSize;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getConfFile() {
		return confFile;
	}

	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}

	public DownloadTask getDownloadTask() {
		return downloadTask;
	}

	public void setDownloadTask(DownloadTask downloadTask) {
		this.downloadTask = downloadTask;
	}	
	
	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public boolean equals(Object o) {
//		if(url.equals(((ResourceInfo)o).getUrl())){
//			return true;
//		}else
		if(StringUtils.isNotBlank(key)&&key.equals(((ResourceInfo)o).getKey())){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void reset() {
		setProgress(0);
		setCompleteSize(0);
		setSpeed(0);
		deleteFiles();
	}
	
	public  void initFile(String fileStr) {
		File file=new File(fileStr);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteFiles() {
		try {
			File file=new File(sourceFile);
			if(file!=null && file.exists()){
				file.delete();
				file.createNewFile();
			}
			file=new File(confFile);
			if(file!=null && file.exists()){
				file.delete();
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "ResourceInfo [startPos=" + startPos + ", endPos=" + endPos
				+ ", completeSize=" + completeSize + ", fileLength="
				+ fileLength + ", progress=" + progress + ", speed=" + speed
				+ ", status=" + status + ", url=" + url + ", fileName="
				+ fileName + ", localPath=" + localPath + ", exception="
				+ exception + ", sourceFile=" + sourceFile + ", confFile="
				+ confFile + ", object=" + object + "]";
	}
	/*********Java序列化存储 配置*********/
//	public BaseResource initFromFile(String filePath){
//		File file = new File(filePath);
//		BaseResource resourceInfo=(BaseResource) CommonUtils.readObject(file);
//		return resourceInfo;
//	}
//	public void updateFileContent(BaseResource resourceInfo){
//		CommonUtils.writeObject(resourceInfo, resourceInfo.getConfFile());
//	}
	/*********JSON格式文件存储配置*********/
	
	public void updateConfFile() {
		if(null==getConfFile())return;
		File file = new File(getConfFile());
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("start_pos", getStartPos());
			jsonObject.put("end_pos", getStartPos());
			jsonObject.put("compelete_size", getCompleteSize());
			jsonObject.put("url",getUrl());
			jsonObject.put("status",getStatus());
			jsonObject.put("localpath",getLocalPath());
			jsonObject.put("filename", getFileName());
			jsonObject.put("fileLength", getFileLength());
			jsonObject.put("object", new JSONObject(getObject()));
			jsonObject.put("key", getKey());
			byte[] buffer = jsonObject.toString().getBytes("UTF-8");
			fileOutputStream.write(buffer);
			fileOutputStream.flush();			

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fileOutputStream = null;
			}
		}
		
	}
	public static ResourceInfo initFromFile(String filePath) {
		File file=new File(filePath);
		JSONObject jsonObject=CommonUtils.readFile2JSONObject(file);
		ResourceInfo resInfo=null;
		if(null!=jsonObject){
			try{
					resInfo=new ResourceInfo();
					resInfo.setUrl(jsonObject.getString("url"));
					resInfo.setLocalPath(jsonObject.getString("localpath"));
					resInfo.setFileName(jsonObject.getString("filename"));
					resInfo.setStartPos(jsonObject.getLong("start_pos"));
					resInfo.setEndPos(jsonObject.getLong("end_pos"));
					resInfo.setCompleteSize(jsonObject.getLong("compelete_size"));
					resInfo.setFileLength(jsonObject.getLong("fileLength"));
					
					resInfo.setProgress((int) (((resInfo.getCompleteSize()) / (float) resInfo.getFileLength()) * 100));
					if(jsonObject.has("object")){
						resInfo.setObject(jsonObject.getJSONObject("object").toString());
						resInfo.setStatus(jsonObject.getInt("status"));
					}else{
						resInfo.setObject(handleOldConf(jsonObject));
						resInfo.setStatus(NDownloadConf.USE_TYPE_STOP);
						resInfo.setLocalPath(file.getParent());
					}
					if(jsonObject.has("key")){
						resInfo.setKey(jsonObject.getString("key"));
					}else{
						resInfo.setKey(StringUtils.md5(jsonObject.getString("pname")+jsonObject.getString("gameVersion")));
					}
					resInfo.initFile();
			}catch(JSONException e){
				Log.e("ResourceInfo", "conf file format error "+file.getAbsolutePath(),e);
			}
		}
		return resInfo;
	}	
	public static String handleOldConf(JSONObject jsonObject){
		Log.d("ResourceInfo", "handleOldConf "+jsonObject);
		JSONObject json=new JSONObject();
		try {
			//json.put("downloadsNum", "");
			json.put("logo", jsonObject.getString("image"));
			json.put("download", jsonObject.getString("url"));
			//json.put("descShort", "");
			json.put("packageSize", CommonUtils.getReadableSize(jsonObject.getLong("fileLength")));
			json.put("version",  jsonObject.getString("gameVersion"));
			//json.put("commentNum", "");
			json.put("id", jsonObject.getString("gameId"));
			json.put("flag", jsonObject.getString("pname"));
			json.put("name", jsonObject.getString("filename"));
			//json.put("serialVersionUID", 1);
			//json.put("pingfen", "");
			//json.put("clazz", "");
			//json.put("freeType", "");
		} catch (JSONException e) {
			//e.printStackTrace();
		}
		return json.toString();
	}
}

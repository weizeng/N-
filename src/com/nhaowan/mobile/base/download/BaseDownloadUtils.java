package com.nhaowan.mobile.base.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import android.os.SystemClock;
import android.text.TextUtils;

import com.haha.frame.net.HttpClientFactory;
import com.haha.frame.utils.Log;
import com.nhaowan.mobile.base.download.PoolManager.Pool;
 

public abstract class BaseDownloadUtils {
	private final static String TAG="BaseDownloadUtils";
	protected  ArrayList<Future<Results>> futureTasks= null;
	protected  ExecutorService executorService =null;
	protected  boolean isThreadPoolClose= false;
	private Pool pool=null;
	public BaseDownloadUtils(String name) {
		if(pool == null) {
			pool  = PoolManager.getPool(name);
			futureTasks =pool.getFutureTasks();
			executorService =pool.getExecutorService();
			isThreadPoolClose =pool.isThreadPoolClose();
		}
	}
	protected boolean isExceptionHappen = false;
	protected ResourceInfo resourceInfo;
	protected int index=-1;
	private boolean isStop=false;
	private final int BUFF_SIZE=2*1024;
	private int executionCount;
	class RealDownTask implements Callable<Results>{
		
		
		public RealDownTask(ResourceInfo ri,int pos) {
			resourceInfo = ri;
			resourceInfo.setStatus(NDownloadConf.USE_TYPE_WAIT);
			index=pos;
		}
		
		public Results call() throws Exception {
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			synchronized (resourceInfo) {
    			if(!isStop)resourceInfo.setStatus(NDownloadConf.USE_TYPE_RUN);
    		}
			doDownloadStart(resourceInfo);
			if (!TextUtils.isEmpty(resourceInfo.getUrl())) {
				isExceptionHappen = false;
				HttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());
//				BasicHttpParams httpParams = new BasicHttpParams();
//				ConnManagerParams.setTimeout(httpParams, 20*1000);
//				HttpConnectionParams.setConnectionTimeout(httpParams, 20*1000);
//				HttpConnectionParams.setSoTimeout(httpParams, 20*1000);
//				HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
//				DefaultHttpClient  httpClient =new DefaultHttpClient(httpParams);
				DefaultHttpClient  httpClient =HttpClientFactory.getDefaultHttpClient();
				httpClient.setHttpRequestRetryHandler(new RetryHandler(30));
				try{
					makeDownloadWithRetries(httpClient,httpContext);
				} catch (IOException e) {
					isExceptionHappen= true;
					resourceInfo.setException(""+e.getCause());
				} finally {
					if(httpClient!=null)
						httpClient.getConnectionManager().shutdown();
					if(!isStop&&!isThreadPoolClose){
						if (!isExceptionHappen){
							doDownloadComplete(resourceInfo);
						} else {
							doDownloadFailed(resourceInfo);
						}
					}else{
						doDownloadUpdateView(resourceInfo);
					}
					doDownloadUpdateFile(resourceInfo);
				}
			} else {
				throw new IllegalArgumentException("url not null");
			}
			return  new Results(index, resourceInfo.getUrl(), null);
		}
		private void makeDownloadWithRetries(DefaultHttpClient  client,HttpContext context)throws ConnectException{
			boolean retry = true;
	        IOException cause = null;
	        HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
	        while (retry&&!isStop) {
	        	try {
	        		synchronized (resourceInfo) {
	        			if(!isStop)resourceInfo.setStatus(NDownloadConf.USE_TYPE_RUN);
	        		}
	    			doDownloadUpdateView(resourceInfo);
	        		makeDownload(client,context);
	                return;
	            } catch (IOException e) {
	                cause = e;
	                retry = retryHandler.retryRequest(cause, executionCount++, context);
	                Log.d(TAG,resourceInfo.getFileName() + "判断重试 retry="+retry);
	            }catch (NullPointerException e) {
	                // there's a bug in HttpClient 4.0.x that on some occasions causes
	                // DefaultRequestExecutor to throw an NPE, see
	                // http://code.google.com/p/android/issues/detail?id=5255
	                cause = new IOException("NPE in HttpClient" + e.getMessage());
	                retry = retryHandler.retryRequest(cause, ++executionCount, context);
	                Log.d(TAG,resourceInfo.getFileName() + "NPE in HttpClient");
	            }
	        }
	        ConnectException ex = new ConnectException();
	        ex.initCause(cause);
	        throw ex;
		}
		private void makeDownload(DefaultHttpClient  httpClient,HttpContext context) throws IOException{
			Log.d(TAG,resourceInfo.getFileName() + "开始下载");
			HttpGet httpGet = new HttpGet(resourceInfo.getUrl());
			httpGet.addHeader("Range", "bytes="+(resourceInfo.getStartPos() + resourceInfo.getCompleteSize())+"-");//+endPos//addHeader(header);//+(endPos)
			Log.d(TAG,resourceInfo.getFileName() + " 断点续传:Range,bytes="+(resourceInfo.getStartPos() + resourceInfo.getCompleteSize())+"-");
			int breakPointLenth = 0;
			InputStream inputStream = null;
			HttpResponse httpResponse = null;
				httpResponse = httpClient.execute(httpGet,context);
				if (httpResponse.getStatusLine().getStatusCode() == 206 ) {
					//HttpStatus.SC_OK
					HttpEntity entity = httpResponse.getEntity();
					long length = entity.getContentLength();
					RandomAccessFile threadfile= new RandomAccessFile(resourceInfo.getSourceFile(), "rwd");  
					if(resourceInfo.getFileLength() == 0){
						//首次下载更新
						Log.d(TAG,resourceInfo.getFileName()+" 初次下载,文件长度是"+length);
						synchronized (resourceInfo) {
							resourceInfo.setFileLength((int)length);
							//更新配置文件
							doDownloadUpdateFile(resourceInfo);
						}
						threadfile.setLength(length);//占空文件
					}else{
						doDownloadContinue(resourceInfo);
					}
//					//更新显示文件大小
					//doDownloadUpdateView(resourceInfo);
					
					inputStream = entity.getContent(); 
					int readCount = 0;
					threadfile.seek(resourceInfo.getStartPos() + resourceInfo.getCompleteSize());  
					byte []  pieceBytes = new byte[BUFF_SIZE];
					long starTime = System.currentTimeMillis();
					long startLength=0;
					if(!isStop)
					while ((readCount = inputStream.read(pieceBytes, 0, BUFF_SIZE)) != -1) {
						if(isStop)break;
						threadfile.write(pieceBytes, 0, readCount);
						resourceInfo.setCompleteSize(resourceInfo.getCompleteSize()+readCount);
						breakPointLenth += readCount;
						resourceInfo.setProgress((int) (((resourceInfo.getCompleteSize())/(float)resourceInfo.getFileLength()) *100));
						startLength+=readCount;
						if((System.currentTimeMillis()-starTime)>1000L) {
							resourceInfo.setSpeed(startLength*1000/(System.currentTimeMillis()-starTime));
							synchronized (resourceInfo) {
			        			if(!isStop)resourceInfo.setStatus(NDownloadConf.USE_TYPE_RUN);
			        		}
							doDownloadUpdateView(resourceInfo);
							starTime=System.currentTimeMillis();
							startLength=0;
						}
					}
					Log.d(TAG,resourceInfo.getFileName()+" 退出下载后文件大小是:"+resourceInfo.getCompleteSize());
				}
		}
	}
	protected void stop(){
		isStop=true;
	}
	protected void start(){
		isStop=false;
	}
	public abstract void doDownloadStart(ResourceInfo resourceInfo);
	public abstract void doDownloadContinue(ResourceInfo resourceInfo);
	public abstract void doDownloadUpdateFile(ResourceInfo resourceInfo);
	public abstract void doDownloadUpdateView(ResourceInfo resourceInfo);
	public abstract void doDownloadComplete(ResourceInfo resourceInfo);
	public abstract void doDownloadFailed(ResourceInfo resourceInfo);
}
class RetryHandler implements HttpRequestRetryHandler {
private static final int RETRY_SLEEP_TIME_MILLIS = 5000;
private static HashSet<Class<?>> exceptionWhitelist = new HashSet<Class<?>>();
private static HashSet<Class<?>> exceptionBlacklist = new HashSet<Class<?>>();

static {
    // Retry if the server dropped connection on us
    exceptionWhitelist.add(NoHttpResponseException.class);
    // retry-this, since it may happens as part of a Wi-Fi to 3G failover
    exceptionWhitelist.add(UnknownHostException.class);
    // retry-this, since it may happens as part of a Wi-Fi to 3G failover
    exceptionWhitelist.add(SocketException.class);

    // never retry timeouts
    exceptionBlacklist.add(InterruptedIOException.class);
    // never retry SSL handshake failures
    exceptionBlacklist.add(SSLHandshakeException.class);
}

private int maxRetries;

public RetryHandler(int maxRetries) {
    this.maxRetries = maxRetries;
}

public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
	Log.d("download retryRequest", "exception:" +exception.getClass()+" executionCount="+executionCount);
    boolean retry = true;
    
    Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
    boolean sent = (b != null && b.booleanValue());
    
    if(executionCount > maxRetries) {
        // Do not retry if over max retry count
        retry = false;
    } else if (exceptionBlacklist.contains(exception.getClass())) {
        // immediately cancel retry if the error is blacklisted
        retry = false;
    } else if (exceptionWhitelist.contains(exception.getClass())) {
        // immediately retry if error is whitelisted
        retry = true;
    }  
    else if (!sent) {
        // for most other errors, retry only if request hasn't been fully sent yet
        retry = true;
    }
    
    if(retry) {
        // resend all idempotent requests
        HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute( ExecutionContext.HTTP_REQUEST );
        if(null==currentReq)return false;
    }
    
    if(retry) {
        SystemClock.sleep(RETRY_SLEEP_TIME_MILLIS);
    } else {
        exception.printStackTrace();
    }

    return retry;
}
}

package com.nhaowan.mobile.base.download;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * TheadPool manager by name
 * @author weixuewu
 *
 */
public class PoolManager {
	protected static final int MAX_COUNT = 10;
	private static ConcurrentHashMap<String,Pool> poolMap=null;
	private static ExecutorService generateExecutorService(final String name){
		ExecutorService executorService= Executors.newFixedThreadPool(MAX_COUNT,new ThreadFactory() {
	        private final AtomicInteger mCount = new AtomicInteger(1);

	        public Thread newThread(final Runnable r) {
	            return new Thread(r, name+"$RealDownTask #" + mCount.getAndIncrement());
	        }
	    });
	    return executorService;
	} 
	public  static Pool renew(String name){
		if(null==poolMap)poolMap=new ConcurrentHashMap<String,Pool>();
		if(poolMap.containsKey(name)){
			poolMap.get(name).close();
			poolMap.remove(name);
		}
		poolMap.put(name, new Pool(name));
		return poolMap.get(name);
	}
	public  static Pool getPool(String name){
		if(null==poolMap)poolMap=new ConcurrentHashMap<String,Pool>();
		if(!poolMap.containsKey(name)){
			poolMap.put(name, new Pool(name));
		}
		return poolMap.get(name);
	}
	public static void clear() {
		if(null!=poolMap){
			poolMap.clear();	
		}
		poolMap=null;
	}
	static class Pool{
		private ArrayList<Future<Results>> futureTasks= null;
		private ExecutorService executorService =null;
		private boolean isThreadPoolClose= false;
		private String name=null;
		Pool(String name){
			this.name=name;
			this.executorService=PoolManager.generateExecutorService(name);
			this.futureTasks=new ArrayList<Future<Results>>();
			this.isThreadPoolClose=false;
		}
		public void close(){
			this.executorService.shutdownNow();
			this.futureTasks.clear();
			this.isThreadPoolClose=true;
			this.executorService=null;
		}
		public boolean isTerminated(){
			return this.executorService.isTerminated();
		}
		public boolean isShutdown(){
			return this.executorService.isShutdown();
		}
		public ArrayList<Future<Results>> getFutureTasks() {
			return futureTasks;
		}
		public void setFutureTasks(ArrayList<Future<Results>> futureTasks) {
			this.futureTasks = futureTasks;
		}
		public ExecutorService getExecutorService() {
			return executorService;
		}
		public void setExecutorService(ExecutorService executorService) {
			this.executorService = executorService;
		}
		public boolean isThreadPoolClose() {
			return isThreadPoolClose;
		}
		public void setThreadPoolClose(boolean isThreadPoolClose) {
			this.isThreadPoolClose = isThreadPoolClose;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}

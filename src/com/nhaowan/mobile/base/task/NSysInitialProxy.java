package com.nhaowan.mobile.base.task;

import java.util.ArrayList;

import android.content.Context;

import com.nhaowan.mobile.base.bean.AppCategoryBean;
import com.nhaowan.mobile.base.bean.GameCategoryBean;
import com.nhaowan.mobile.base.log.Log;
import com.nhaowan.mobile.base.response.HomeScreenResponse;
import com.nhaowan.mobile.base.response.UpdateAppResponse;
import com.nhaowan.mobile.base.task.IProxyTask.Status;

/**
 * 获取系统配置的代理类
 * 
 * @author leo
 * 
 */
public class NSysInitialProxy {

	public interface INSConf {

		void onProgress(Object t);

		void onFailed(String error);

		void onCompleteAll();

	}

	private static String TAG = NSysInitialProxy.class.getSimpleName();

	// 游戏种类
	private ArrayList<GameCategoryBean> gameCategoryList;
	// 栏目种类
	private ArrayList<AppCategoryBean> appCategoryList;
	private IProxyTask versionTask, homeTask, gameTask, appTask;
	static NSysInitialProxy proxy;

	public static NSysInitialProxy getInstance() {
		if (proxy == null) {
			proxy = new NSysInitialProxy();
		}
		return proxy;
	}

	/**
	 * 初始化系统配置
	 * 
	 * @param mContext
	 * @param fetcher
	 */
	@SuppressWarnings("rawtypes")
	public void initSysConf(final Context mContext, final INSConf fetcher) {

		versionTask = new VersionUpdateTask(mContext) {

			@Override
			public void onComplete(UpdateAppResponse t) {
				fetcher.onProgress(t);
				status = Status.COMPLETE;
				Log.d(TAG, "VersionUpdateTask has complete");
				checkTaskAllComplete(fetcher);

			}

			@Override
			public void onFailed(String error) {
				status = Status.FAILED;
				Log.d(TAG, "VersionUpdateTask has failed");
				checkTaskAllComplete(fetcher);
			}

		};
		versionTask.onTaskStart();

		homeTask = new HomeScreenTask(mContext) {

			@Override
			public void onComplete(HomeScreenResponse t) {
				Log.d(TAG, "HomeScreenTask has complete");
				fetcher.onProgress(t);
				status = Status.COMPLETE;
				checkTaskAllComplete(fetcher);
			}

			@Override
			public void onFailed(String error) {
				status = Status.FAILED;
				Log.d(TAG, "HomeScreenTask has failed");
				checkTaskAllComplete(fetcher);
			}

		};
		homeTask.onTaskStart();

		gameTask = new GameCategoryTask(mContext) {
			@Override
			public void onProgress(ArrayList<GameCategoryBean> t) {
				super.onProgress(t);
				gameCategoryList = ((ArrayList<GameCategoryBean>) t.clone());
			}

			@Override
			public void onFailed(String error) {
				Log.d(TAG, "GameCategoryTask has complete");
				status = Status.FAILED;
				checkTaskAllComplete(fetcher);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onComplete(ArrayList<GameCategoryBean> t) {
				status = Status.COMPLETE;
				Log.d(TAG, "GameCategoryTask has complete");
				gameCategoryList = ((ArrayList<GameCategoryBean>) t.clone());
				// fetcher.onProgress(gameCategoryList);
				checkTaskAllComplete(fetcher);
			}

		};
		gameTask.onTaskStart();

		appTask = new AppCategoryTask(mContext, true) {

			@SuppressWarnings("unchecked")
			@Override
			public void onComplete(ArrayList<AppCategoryBean> t) {
				Log.d(TAG, "AppCategoryTask has complete");
				if (fetcher != null) {
					appCategoryList = ((ArrayList<AppCategoryBean>) t.clone());
					if (fetcher != null) {
						fetcher.onProgress(appCategoryList);
					}
					status = Status.COMPLETE;
					checkTaskAllComplete(fetcher);
				}

			}

			@Override
			public void onFailed(String error) {
				Log.d(TAG, "AppCategoryTask has failed");
				status = Status.FAILED;
				checkTaskAllComplete(fetcher);
			}

		};
		appTask.onTaskStart();
	}

	void checkTaskAllComplete(INSConf fetcher) {
		if (versionTask.getStatus() == Status.COMPLETE && homeTask.getStatus() == Status.COMPLETE
				&& gameTask.getStatus() == Status.COMPLETE && appTask.getStatus() == Status.COMPLETE) {
			if (fetcher != null) {
				fetcher.onCompleteAll();
			}
		} else if (versionTask.getStatus() != Status.GOING && homeTask.getStatus() != Status.GOING
				&& gameTask.getStatus() != Status.GOING && appTask.getStatus() != Status.GOING) {
			if (fetcher != null) {
				fetcher.onCompleteAll();
			}
		}
	}

	public ArrayList<GameCategoryBean> getGameCategoryList() {
		return gameCategoryList;
	}

	public ArrayList<AppCategoryBean> getAppCategoryList() {
		return appCategoryList;
	}

	public ArrayList<String> getAppCategoryNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (AppCategoryBean bean : getAppCategoryList()) {
			names.add(bean.getCatname());
		}
		return names;
	}

	public String getGameCategoryList(String catid) {
		for (int i = 0; gameCategoryList != null && i < gameCategoryList.size(); i++) {
			if (catid.equalsIgnoreCase(gameCategoryList.get(i).getCatid())) {
				return gameCategoryList.get(i).getCatname();
			}
		}
		return "未知";
	}
}

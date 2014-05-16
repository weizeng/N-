package com.nhaowan.mobile.base.view;

import it.gmariotti.cardslib.library.view.StickHeaderListView;

import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.AsyncHttpResponseHandler;
import com.haha.frame.utils.JsonUtils;
import com.haha.frame.utils.Log;
import com.haha.frame.widget.ListResult;
import com.haha.frame.widget.LoadMoreView;

/**
 * 具有header，和下拉加载更多的业务逻辑的listview
 * 
 * @author leo
 * 
 */
public class GameNewsLoadMoreListView<T extends ListResult> extends StickHeaderListView {

	private Context mContext;
	private AbsLoadDataManager<T> mListener = null;
	private int currentPage = 1;
	private int currentDataSize;
	private int totalPage;
	private int firstVisibleIndex = 0; //
	private int lastVisibleIndex = 0; // 显示的最后一条
	private Object object = new Object();
	private LoadMoreView loadMoreView;
	public final static String TIME_UPDATE = "P_TIME_UPDATE";

	public GameNewsLoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public GameNewsLoadMoreListView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public GameNewsLoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public void setOnScorllIdleListener(AbsLoadDataManager<T> listener) {
		mListener = listener;
	}

	private void init() {

		setOnScrollListener(new OnScrollListener() {
			// 记录上次的最后一个item位置，用来判断向上还是向下滑动
			private int tempLastItem = 0;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 数据集最后一项的索引,从0计数
				int itemsLastIndex = currentDataSize - 1;
				// 增加Footer 和 header 最后一项索引 计数从0开始
				int lastIndex = itemsLastIndex + getHeaderViewsCount() + 1;
				// Log.d("listview",
				// "lastVisibleIndex:"+lastVisibleIndex+",lastIndex:"+lastIndex);
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleIndex == lastIndex) {
					// 加载更多
					if (mListener != null) {
						synchronized (object) {
							if (loadMoreView != null && loadMoreView.getVisibility() == View.GONE) {
								loadMoreView.setVisibility(View.VISIBLE);
 							}
							loadMoreView.setLoadMoreStatus();
							onRefresh(false);
						}
					}
				}

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					int visibleFirstItem = firstVisibleIndex;
					int visibleLastItem = lastVisibleIndex;

					for (; visibleFirstItem < visibleLastItem + 1 && visibleFirstItem < currentDataSize;) {
						if (mListener != null) {
							try {
								mListener.onUpdateBitmap(visibleFirstItem);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						visibleFirstItem++;
					}
					tempLastItem = lastVisibleIndex;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {

				// 从0计数得到
				lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
				if (tempLastItem != lastVisibleIndex) {
					if (mListener != null) {
						if (tempLastItem > lastVisibleIndex) {
							// 往前
							mListener.onListScorllStateChanged(true);
						} else {
							// 往后
							mListener.onListScorllStateChanged(false);
						}
					}
				}
				tempLastItem = lastVisibleIndex;

				GameNewsLoadMoreListView.this.firstVisibleIndex = firstVisibleItem;
			}
		});

		loadMoreView = new LoadMoreView(mContext) {

			@Override
			public void doClick(View v) {
				onRefresh(false);
			}
		};
	}

	public void cancel() {
		// AsyncHttpManager.getInstance().cancelRequests(mContext, true);
	}

	/** 手动加载footer */
	private void addCusFooterView(View view) {
		if (getFooterViewsCount() == 0) {
			view.setVisibility(View.VISIBLE);
//			this.addFooterView(view);
			addFooterView(view);
		}
	}

	public void addCusFooterView() {
		addCusFooterView(loadMoreView.getView());
	}
	
	public void setCurrentDataSize(int currentDataSize) {
		this.currentDataSize = currentDataSize;
	}
	
	public int getCurrentDataSize() {
		return currentDataSize;
	}

	/** 手动移除footer */
	private void removeFooterView() {
		if (getFooterViewsCount() > 0 && loadMoreView != null) {
			removeFooterView(loadMoreView.getView());
		}
	}

	public synchronized void onRefresh(final boolean topRefresh) {
		/** stores */
		final RefreshParams params = mListener.getRefreshUrl(topRefresh ? 1 : currentPage, topRefresh);

		if (params != null && TextUtils.isEmpty(params.url)) {
			return;
		}

//		if (!topRefresh) {
//			addCusFooterView(loadMoreView.getView());
//		}
		AsyncHttpManager.getInstance().get(mContext, params.urlPath, params.submitMap, params.url,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFinish() {
						super.onFinish();
					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(int statusCode, String retRes) {
//						if(!topRefresh) {
//							removeFooterView();
//						}
						if (topRefresh) {
							currentPage = 1;
						}
						Log.d("PLIST", params.url + " >>>>>>>>> " + retRes);

						T t = null;
						try {
							if (params != null && params.response != null) {
								// by leo 4.21
								t = (T) JsonUtils.parserToObjectByAnnotation(params.response, retRes);

							}
							t = (T) ((t == null) ? new ListResult() {

								@Override
								public int getTotalPage() {
									return 0;
								}

								@Override
								public int getDataSize4EachRequest() {
									return 0;
								}

							} : t);
							mListener.onSuccess(statusCode, t, retRes, topRefresh);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (t != null) {
							totalPage = t.getTotalPage();
							if (topRefresh) {
								currentDataSize = t.getDataSize4EachRequest();
							} else {
								currentDataSize = currentDataSize + t.getDataSize4EachRequest();
							}
							if (currentPage < totalPage) {
								addCusFooterView(loadMoreView.getView());
							} else {
								loadMoreView.setViewTag(0);
								removeFooterView();
								mListener.onEnd();
							}
						}
						if (currentPage <= totalPage) {
							currentPage++;
						}
					}

					@Override
					public void onFailure(Throwable error, String content) {
						super.onFailure(error, content);
						loadMoreView.setReloadStatus();
					}
				});

	}

	public abstract static class AbsLoadDataManager<T extends ListResult> {
		public void onListScorllStateChanged(boolean isPull) {
			// isPull true 上拉
		}

		public abstract void onUpdateBitmap(int visibleItem);

		public abstract void onSuccess(int statusCode, T retRes, String retStr, boolean topRefresh);// 返回size

		public abstract RefreshParams<T> getRefreshUrl(int currentPage, boolean topRefresh);

		public void onLoadDataFailed(boolean topRefresh) {};
		
		public void onEnd() {};
	}

	public static class RefreshParams<T> {
		public String url;
		public String urlPath;
		public HashMap<String, String> submitMap = new HashMap<String, String>();;
		public Class<T> response;
	}

}

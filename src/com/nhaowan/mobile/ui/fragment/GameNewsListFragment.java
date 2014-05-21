package com.nhaowan.mobile.ui.fragment;

import it.gmariotti.cardslib.library.internal.Card;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.leo.net.AsyncHttpManager;
import com.leo.net.IFetcher;
import com.leo.utils.FileSerializable;
import com.leo.utils.FileUtils;
import com.leo.widget.AutoGallery;
import com.leo.widget.FlowIndicator;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.BaseFragment;
import com.nhaowan.mobile.base.bean.AppCategoryBean;
import com.nhaowan.mobile.base.bean.NewsBean;
import com.nhaowan.mobile.base.bean.TopBean;
import com.nhaowan.mobile.base.response.NewsBusinessResponse;
import com.nhaowan.mobile.base.task.NSysInitialProxy;
import com.nhaowan.mobile.base.utils.Contants;
import com.nhaowan.mobile.base.view.AbsLoadDataManager;
import com.nhaowan.mobile.base.view.CardHeaderMoreListView;
import com.nhaowan.mobile.base.view.RefreshParams;
import com.nhaowan.mobile.ui.adapter.GameNewHeaderArrayAdapter;
import com.nhaowan.mobile.ui.adapter.TopGalleryAdapter;
import com.nhaowan.mobile.ui.logic.GameNewsManager;

public class GameNewsListFragment extends BaseFragment implements OnRefreshListener, OnNavigationListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private CardHeaderMoreListView<NewsBusinessResponse> mListView;
	private Context mContext;
	private GameNewHeaderArrayAdapter mAdapter;

	private ArrayList<NewsBean> boxerList = new ArrayList<NewsBean>();
	private ArrayList<TopBean> topList = new ArrayList<TopBean>();
	private TopGalleryAdapter galleryAdapter;
	private int circleSelectedPosition = 0;
	private View headView, emptyView;
	private AutoGallery gallery;
	private FlowIndicator galleryFlowIndicator;
	private boolean isInitHeader = true;
	private String serialTypeFile;
	private AppCategoryBean categoryBean;

	@Override
	public int getTitleResourceId() {
		return R.string.app_name;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		headView = inflater.inflate(R.layout.gallery_header, null);
		emptyView = new View(getActivity());
		return inflater.inflate(R.layout.fragment_game_news, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (gallery != null) {
			gallery.start();
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		findViews(view);
		initHeader();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		populateNavigationList();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("news", boxerList);
		outState.putParcelableArrayList("top", topList);
	}

	private void fetchNews(Bundle savedInstanceState, boolean isFetchTopNews) {
		// 本地取出数据
		GameNewsManager.fetchLocalNews(savedInstanceState, categoryBean.getCatid() + "",
				new IFetcher<ArrayList<NewsBean>>() {

					@Override
					public void onFetcherStart() {
					}

					@Override
					public void onFetcherSuccess(ArrayList<NewsBean> t) {
						if (t != null && t.size() > 0) {
							boxerList = t;
							mAdapter.clear();
							mAdapter.addAll(GameNewsManager.fetchCardsByType(mContext, boxerList));
						}
					}

					@Override
					public void onFetcherFailed(String error) {
					}

				});
		if (isFetchTopNews) {
			onRefreshTopList(savedInstanceState);
		}
	}

	private void onRefreshTopList(Bundle savedInstanceState) {

		GameNewsManager.fetchTopList(savedInstanceState, mContext, new IFetcher<ArrayList<TopBean>>() {

			@Override
			public void onFetcherStart() {
			}

			@Override
			public void onFetcherSuccess(ArrayList<TopBean> t) {
				topList = t;
				galleryFlowIndicator.setCount(t.size());
				galleryAdapter.setDataList(t);
				galleryAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFetcherFailed(String error) {
			}
		});
	}

	private void initListScorll() {
		mListView.setOnScorllIdleListener(new AbsLoadDataManager<NewsBusinessResponse>() {

			@Override
			public void onUpdateBitmap(int visibleItem) {
			}
			
			public void onLoadDataFailed(boolean topRefresh) {
				mPullToRefreshLayout.setRefreshComplete();
			}
			
			@Override
			public void onSuccess(int statusCode, NewsBusinessResponse retRes, String retStr,
					boolean topRefresh) {
				mPullToRefreshLayout.setRefreshComplete();
				if (retRes != null && retRes.getRet() == 0) {
					// TODO 太复杂 需要简化
					ArrayList<NewsBean> temp = new ArrayList<NewsBean>();
					for (NewsBean newsBean : boxerList) {
						if (newsBean.isReaded()) {
							temp.add(newsBean);
						}
					}
					// 检查之前是否已经读取过
					for (int i = 0; i < retRes.getList().size(); i++) {
						NewsBean newsBean = retRes.getList().get(i);
						newsBean.setTid(i);
						if (categoryBean != null) {
							newsBean.setDisplayStyle(!TextUtils.isEmpty(newsBean.getThumb()) ? Integer
									.parseInt(categoryBean.getStyle()) : NewsBean.STYLE_BMAP_NO);
						}
						for (NewsBean tempBean : temp) {
							if (tempBean.getCatid() == newsBean.getCatid()
									&& tempBean.getId() == newsBean.getId()) {
								newsBean.setReaded(true);
								break;
							}
						}
					}

					mAdapter.clear();
					if (topRefresh) {
						boxerList.clear();
						boxerList = retRes.getList();
						FileSerializable.serialize2Local(GameNewsListFragment.this.boxerList,
								GameNewsListFragment.this.serialTypeFile);
					} else {
						boxerList.addAll(retRes.getList());
					}
					mAdapter.addAll(GameNewsManager.fetchCardsByType(mContext, boxerList));
				}
			}

			@Override
			public RefreshParams<NewsBusinessResponse> getRefreshUrl(int currentPage, boolean topRefresh) {
				RefreshParams<NewsBusinessResponse> params = new RefreshParams<NewsBusinessResponse>();
				params.url = Contants.SERVICE_ROOT + "/app/news";
				params.response = NewsBusinessResponse.class;
				params.urlPath = "/app/news";
				params.submitMap.put("catid", "" + categoryBean.getCatid());
				params.submitMap.put("p", currentPage + "");
				return params;
			}

		});
	}

	private void initHeader() {
		// 初始化滚动横幅
		gallery = (AutoGallery) headView.findViewById(R.id.homepage_image_gallery);
		galleryFlowIndicator = (FlowIndicator) headView.findViewById(R.id.homepage_circle_indicator);
		galleryAdapter = new TopGalleryAdapter(mContext);

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (topList == null || topList.size() == 0) {
					return;
				}
				circleSelectedPosition = position % topList.size();
				galleryFlowIndicator.setSeletion(circleSelectedPosition);

//				Bundle bundle = new Bundle();
//				bundle.putParcelable("news", fff);
//
//				mContext.startActivity(new Intent(getActivity(), GameDetailActivity.class));
						
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		gallery.setAdapter(galleryAdapter);
	}

	@SuppressWarnings("unchecked")
	private void findViews(View view) {
		// Set the adapter
		mAdapter = new GameNewHeaderArrayAdapter(mContext, new ArrayList<Card>());

		mListView = (CardHeaderMoreListView<NewsBusinessResponse>) view
				.findViewById(R.id.carddemo_extra_sticky_list);
		 
		// 初始化下拉刷新
		mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.carddemo_extra_ptr_layout);
		ActionBarPullToRefresh.from(getActivity()).theseChildrenArePullable(mListView.getWrappedList())
				.listener(this).setup(mPullToRefreshLayout);
		
		initListScorll();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (gallery != null) {
			gallery.stop();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		FileSerializable.serialize2Local(boxerList, serialTypeFile);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		AsyncHttpManager.getInstance().cancelRequests(mContext, true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mListView != null) {
			mListView.cancel();
		}
		if (boxerList != null) {
			boxerList.clear();
		}
		if (topList != null) {
			topList.clear();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main_fragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	private void populateNavigationList() {

		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getActionBar()
				.getThemedContext(), R.layout.dropdown_app_category_layout, android.R.id.text1,
				NSysInitialProxy.getInstance().getAppCategoryNames());
		getActivity().getActionBar().setListNavigationCallbacks(adapter, this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mAdapter != null && mContext != null) {
			// sa.refetchAppImgParam(CommonUtils.getDefaultDisplay(getActivity()).getWidth(),
			// (CommonUtils.getDefaultDisplay(mContext).getHeight() -
			// CommonUtils
			// .getStatusAndTitleHeight(mContext)) / 2);
		}
	}

	@Override
	public void onRefreshStarted(View view) {
		mListView.onRefresh(true);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long arg1) {
		removeOrAddHeaderByCategory(position);
		categoryBean = NSysInitialProxy.getInstance().getAppCategoryList().get(position);
		serialTypeFile = Contants.PATH_SDCARD_FILES + categoryBean.getCatid() + ".iw";
		
		fetchNews(null, position == 0);
		if(!FileUtils.checkFileExists(serialTypeFile)) {
			mPullToRefreshLayout.setRefreshing(true);
		}
		mListView.onRefresh(true);
		return true;
	}

	private void removeOrAddHeaderByCategory(int position) {
		if (mListView.getHeaderViewsCount() > 0) {
			mListView.removeHeaderView(emptyView);
			mListView.removeHeaderView(headView);
		}
		if (position == 0) {
			mListView.addHeaderView(headView);
		} else {
			mListView.addHeaderView(emptyView);
		}
		mAdapter.setNeedHeaderView(position == 0);
		if (mListView != null) {
			mListView.setAdapter(mAdapter);
		}
	}
}

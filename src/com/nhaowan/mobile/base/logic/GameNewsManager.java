package com.nhaowan.mobile.base.logic;

import it.gmariotti.cardslib.library.internal.Card;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;

import com.haha.frame.net.AsyncHttpManager;
import com.haha.frame.net.IFetcher;
import com.haha.frame.net.IWAsyncHttpResponseHandler;
import com.haha.frame.utils.FileSerializable;
import com.nhaowan.mobile.base.bean.NewsBean;
import com.nhaowan.mobile.base.bean.TopBean;
import com.nhaowan.mobile.base.cards.GameNewInnerBigPicCard;
import com.nhaowan.mobile.base.cards.GameNewInnerSmallCard;
import com.nhaowan.mobile.base.response.TopNewsResponse;
import com.nhaowan.mobile.base.utils.Contants;

public class GameNewsManager {

	@SuppressWarnings("unchecked")
	public static synchronized void fetchTopList(Bundle savedInstanceState, Context mContext,
			final IFetcher<ArrayList<TopBean>> iFetcher) {
		if (savedInstanceState != null) {
			ArrayList<TopBean> boxerList = savedInstanceState.getParcelableArrayList("top");
			iFetcher.onFetcherSuccess(boxerList == null ? new ArrayList<TopBean>() : boxerList);
		} else {
			try {
				ArrayList<TopBean> topList = (ArrayList<TopBean>) FileSerializable
						.readFromLocal(Contants.SERIAL_TOP_FILE);
				if (iFetcher != null && topList.get(0).getCatid() != 0) {
					iFetcher.onFetcherSuccess(topList);
				}
			} catch (Exception e) {
				// exception
			}

			// ENTRY 获取Top
			HashMap<String, String> params = new HashMap<String, String>();
			AsyncHttpManager.getInstance().get(mContext, "/app/top", params,
					Contants.SERVICE_ROOT + "/app/top",
					new IWAsyncHttpResponseHandler<TopNewsResponse>(TopNewsResponse.class) {

						@Override
						public void onSuccess(TopNewsResponse t) {
							if (t != null && t.getRet() == 0) {
								FileSerializable.serialize2Local(t.getList(), Contants.SERIAL_TOP_FILE);
								if (iFetcher != null) {
									iFetcher.onFetcherSuccess(t.getList());
								}
							}
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							if (iFetcher != null) {
								iFetcher.onFetcherFailed();
							}
						}
					});
		}
	}

	@SuppressWarnings("unchecked")
	public static synchronized void fetchLocalNews(Bundle savedInstanceState, String catid,
			IFetcher<ArrayList<NewsBean>> iFetcher) {
		if (savedInstanceState != null) {
			ArrayList<NewsBean> boxerList = savedInstanceState.getParcelableArrayList("news");
			iFetcher.onFetcherSuccess(boxerList == null ? new ArrayList<NewsBean>() : boxerList);
		} else {
			String serialTypeFile = Contants.PATH_SDCARD_FILES + catid + ".iw";
			try {
				ArrayList<NewsBean> tmp = (ArrayList<NewsBean>) FileSerializable
						.readFromLocal(serialTypeFile);
				iFetcher.onFetcherSuccess(tmp);
				return;
			} catch (Exception e) {
			}
			iFetcher.onFetcherSuccess(new ArrayList<NewsBean>());
		}
	}

	public static ArrayList<Card> fetchCardsByType(Context mContext, ArrayList<NewsBean> boxerList) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (NewsBean bean : boxerList) {
			if (bean.getDisplayStyle() == NewsBean.STYLE_BMAP_LARGE) {
				GameNewInnerBigPicCard card = new GameNewInnerBigPicCard(mContext, bean);
				cards.add(card);
			} else if (bean.getDisplayStyle() == NewsBean.STYLE_BMAP_NO) {
				GameNewInnerBigPicCard card = new GameNewInnerBigPicCard(mContext, bean);
				cards.add(card);
			} else if (bean.getDisplayStyle() == NewsBean.STYLE_BMAP_SMALL) {
				GameNewInnerSmallCard card = new GameNewInnerSmallCard(mContext, bean);
				cards.add(card);
			} else if (bean.getDisplayStyle() == NewsBean.STYLE_BMAP_HOT) {
				GameNewInnerBigPicCard card = new GameNewInnerBigPicCard(mContext, bean);
				cards.add(card);
			}
		}
		return new ArrayList<Card>(cards);
	}
}

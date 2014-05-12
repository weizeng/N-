package com.nhaowan.mobile.base.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haha.frame.common.CommonUtils;
import com.haha.frame.widget.AutoImageView;
import com.haha.frame.widget.AutoImageView.IThumbViewListener;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.bean.NewsBean;
import com.nhaowan.mobile.base.task.NSysInitialProxy;

public class GameNewInnerSmallCard extends Card {
	private NewsBean newsBean;
	private Context mContext;
	private TextView titleView;
	private TextView desView;
//	private TextView timeView;
	private AutoImageView photoView;
	private TextView cateView;
//	private TextView subtitleView;
	

	public GameNewInnerSmallCard(Context context, NewsBean bean) {
		super(context, R.layout.new_listview_item_small);
		this.newsBean = bean;
		this.mContext = context;
		init();
	}

	private void init() {
		
		setOnClickListener(new OnCardClickListener() {
			
			@Override
			public void onClick(Card card, View view) {
				CommonUtils.showToast(getContext(), "跳转");
			}
		});
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {
		// super.setupInnerViewElements(parent, view);
		titleView = (TextView) view.findViewById(R.id.common_list_item_text1);
		desView = (TextView) view.findViewById(R.id.common_list_item_text2);
//		timeView = (TextView) view.findViewById(R.id.common_list_item_text3);
		photoView = (AutoImageView) view.findViewById(R.id.common_list_item_image);
		cateView = (TextView) view.findViewById(R.id.common_list_item_category_tv);
		
		
//		subtitleView = (TextView) view.findViewById(R.id.common_list_item_subtitle);

		if (newsBean.isReaded()) {
			titleView.setTextColor(Color.GRAY);
			desView.setTextColor(Color.GRAY);
		} else {
			titleView.setTextColor(Color.BLACK);
			desView.setTextColor(Color.BLACK);
		}
		titleView.setText(newsBean.getTitle());
//		timeView.setText(DateUtil.showTime(new Date(newsBean.getCreateTime() * 1000), null));
		desView.setText(newsBean.getDescription());
		cateView.setText(NSysInitialProxy.getInstance().getGameCategoryList(newsBean.getCatid() + ""));
		if (!TextUtils.isEmpty(newsBean.getSubTitle())) {
//			subtitleView.setText(newsBean.getSubTitle());
//			subtitleView.setVisibility(View.VISIBLE);
		}
		photoView.setImageBitmap(null);
		photoView.setUserThumb(newsBean.getThumb(), new IThumbViewListener() {

			@Override
			public void callback(Bitmap bitmap) {
				photoView.setImageBitmap(bitmap);
			}
		});
	}

}

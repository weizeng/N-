package com.nhaowan.mobile.base.cards;

import it.gmariotti.cardslib.library.internal.Card;

import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haha.frame.common.CommonUtils;
import com.haha.frame.utils.DateUtil;
import com.haha.frame.widget.AutoImageView;
import com.haha.frame.widget.AutoImageView.IThumbViewListener;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.bean.NewsBean;
import com.nhaowan.mobile.base.task.NSysInitialProxy;

public class GameNewInnerBigPicCard extends Card {
	private NewsBean newsBean;
	private Context mContext;
	private TextView titleView;
	private TextView desView;
//	private TextView timeView;
	private TextView readedViews;
	private AutoImageView photoView;
	private TextView cateView;
//	private TextView subtitleView;
	private int appWidth, imgMaxHeight;
	private TextView commentView;
	
	public GameNewInnerBigPicCard(Context context, NewsBean bean) {
		super(context, R.layout.new_listview_item_bigpic);
		this.newsBean = bean;
		this.mContext = context;
		init();
	}

	private void init() {
		appWidth = CommonUtils.getDefaultDisplay(mContext).getWidth();
		imgMaxHeight = (CommonUtils.getDefaultDisplay(mContext).getHeight() - CommonUtils
				.getStatusAndTitleHeight(mContext)) / 2;
		
		setOnClickListener(new OnCardClickListener() {
			
			@Override
			public void onClick(Card card, View view) {
				CommonUtils.showToast(getContext(), "跳转");
			}
		});
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		titleView = (TextView) view.findViewById(R.id.common_list_item_text1);
		desView = (TextView) view.findViewById(R.id.common_list_item_text2);
		readedViews = (TextView) view.findViewById(R.id.new_item_zan_count);
		photoView = (AutoImageView) view.findViewById(R.id.common_list_item_image);
		cateView = (TextView) view.findViewById(R.id.common_list_item_category_tv);
//		subtitleView = (TextView) view.findViewById(R.id.common_list_item_subtitle);
		commentView = (TextView) view.findViewById(R.id.new_item_comment);
		
		if (newsBean.isReaded()) {
			titleView.setTextColor(Color.GRAY);
			desView.setTextColor(Color.GRAY);
		} else {
			titleView.setTextColor(Color.BLACK);
			desView.setTextColor(Color.BLACK);
		}
		titleView.setText(newsBean.getTitle());
		readedViews.setText("+"+newsBean.getViews());
//		timeView.setText(DateUtil.showTime(new Date(newsBean.getCreateTime() * 1000), null));
		desView.setText(newsBean.getDescription());
		cateView.setText(NSysInitialProxy.getInstance().getGameCategoryList(newsBean.getCatid() + ""));
//		if (!TextUtils.isEmpty(newsBean.getSubTitle())) {
//			subtitleView.setText(newsBean.getSubTitle());
//			subtitleView.setVisibility(View.VISIBLE);
//		}
		photoView.setImageBitmap(null);
		photoView.setUserThumb(newsBean.getThumb(), new IThumbViewListener() {

			@Override
			public void callback(Bitmap bitmap) {
				photoView.setMeasureBitmap(bitmap == null ? null : bitmap,
						GameNewInnerBigPicCard.this.appWidth, GameNewInnerBigPicCard.this.imgMaxHeight);
			}
		});
		
		commentView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtils.showToast(getContext(), "pinglun");
			}
		});
	}

}

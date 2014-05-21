package com.nhaowan.mobile.ui.adapter;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.AbsStickHeaderAdapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nhaowan.mobile.base.view.GameSectionView;

public class GameNewHeaderArrayAdapter extends AbsStickHeaderAdapter {
	boolean isNeedHeaderView;
	public GameNewHeaderArrayAdapter(Context mContext, List<Card> cards) {
		super(mContext, cards);
	}

	public void setNeedHeaderView(boolean isNeedHeaderView) {
		this.isNeedHeaderView = isNeedHeaderView;
	}

	class HeaderViewHolder {
		GameSectionView cardView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
		if(isNeedHeaderView) {
			HeaderViewHolder headerViewHolder;
			if (convertView == null) {
				headerViewHolder = new HeaderViewHolder();
				convertView = new GameSectionView(getContext());
				convertView.setTag(headerViewHolder);
			} else {
				headerViewHolder = (HeaderViewHolder) convertView.getTag();
			}
		} else {
			convertView = new View(mContext);
		}
		return convertView;
	}

	@Override
	public Card getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getHeaderId(int position) {
		Card card = getItem(position);
		return 1;
	}
}

package com.nhaowan.mobile.ui.adapter;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.AbsStickHeaderAdapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.view.GameSectionView;

public class GameNewHeaderArrayAdapter extends AbsStickHeaderAdapter {
	public GameNewHeaderArrayAdapter(Context mContext, List<Card> cards) {
		super(mContext, cards);
	}

	class HeaderViewHolder {
		GameSectionView cardView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
		HeaderViewHolder headerViewHolder;
		if (convertView == null) {
			headerViewHolder = new HeaderViewHolder();
			convertView = new GameSectionView(getContext());
//			convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_header_layout, null);
//			headerViewHolder.cardView = (GameSectionView) convertView
//					.findViewById(R.id.game_heard_cardview);
			convertView.setTag(headerViewHolder);
		} else {
			headerViewHolder = (HeaderViewHolder) convertView.getTag();
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

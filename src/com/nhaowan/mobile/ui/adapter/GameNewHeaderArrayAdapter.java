package com.nhaowan.mobile.ui.adapter;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.cards.GameSectionCardView;

public class GameNewHeaderArrayAdapter extends CardArrayAdapter implements StickyListHeadersAdapter {
	public GameNewHeaderArrayAdapter(Context mContext, List<Card> cards) {
		super(mContext, cards);
	}

	class HeaderViewHolder {
		GameSectionCardView cardView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup viewGroup) {
		HeaderViewHolder headerViewHolder;
		if (convertView == null) {
			headerViewHolder = new HeaderViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_header_cardview, null);
			headerViewHolder.cardView = (GameSectionCardView) convertView
					.findViewById(R.id.game_heard_cardview);
			convertView.setTag(headerViewHolder);
		} else {
			headerViewHolder = (HeaderViewHolder) convertView.getTag();
		}

		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		Card card = getItem(position);
		return 1;
	}
}

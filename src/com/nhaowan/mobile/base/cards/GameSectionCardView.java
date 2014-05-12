package com.nhaowan.mobile.base.cards;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardHeader.OnClickCardHeaderPopupMenuListener;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.component.CardHeaderView;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haha.frame.common.CommonUtils;
import com.haha.frame.widget.AutoImageView;
import com.nhaowan.mobile.R;

public class GameSectionCardView extends CardView {
	private Context context;
	private AutoImageView userPhotoView;
	private TextView nameTextView;
	private TextView tagTextView;

	public GameSectionCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GameSectionCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameSectionCardView(Context context) {
		super(context);
		init(context);
	}

	private void init(final Context context) {
		GameCard gameCard = new GameCard(context, R.layout.game_header_card);
		setCard(gameCard);
	}

	static class GameCard extends Card {
		private WeakReference<Context> contextReference;

		public GameCard(Context context, int innerLayout) {
			super(context, innerLayout);
			contextReference = new WeakReference<Context>(context);
			init2();
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {
			CardHeaderView cardHeaderView = (CardHeaderView) view.findViewById(R.id.game_name_cardHeaderView);

			CardHeader gameNameCardHeader = new CardHeader(contextReference.get());
			gameNameCardHeader.setTitle("2048");

			gameNameCardHeader.setPopupMenu(R.menu.menu_game_section,
					new OnClickCardHeaderPopupMenuListener() {
						//
						@Override
						public void onMenuItemClick(BaseCard card, MenuItem item) {

						}
					});
			cardHeaderView.addCardHeader(gameNameCardHeader);
			
			view.findViewById(R.id.game_photo_imageview).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					CommonUtils.showToast(getContext(), "game_photo_imageview");
				}
			});
		}

		public void init2() {
			setOnClickListener(new OnCardClickListener() {

				@Override
				public void onClick(Card card, View view) {
					CommonUtils.showToast(contextReference.get(), "click");
				}
			});
		}
	}

}
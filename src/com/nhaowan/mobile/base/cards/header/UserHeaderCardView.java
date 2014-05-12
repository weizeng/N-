package com.nhaowan.mobile.base.cards.header;

import it.gmariotti.cardslib.library.view.component.CardHeaderView;
import android.content.Context;
import android.util.AttributeSet;

import com.nhaowan.mobile.R;

public class UserHeaderCardView extends CardHeaderView {
	public UserHeaderCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public UserHeaderCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public UserHeaderCardView(Context context) {
		super(context);
		init(context);
	}

	void init(Context context) {
		addCardHeader(new UserHeaderCard(context));
	}

}

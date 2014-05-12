package com.nhaowan.mobile.ui.preference;

import it.gmariotti.cardslib.library.view.CardView;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.cards.UserSectionCard;
import com.nhaowan.mobile.base.cards.UserSectionCard.IUserLogin;

public class UserPreference extends Preference {

	private Context context;

	public UserPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public UserPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public UserPreference(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		return LayoutInflater.from(getContext()).inflate(R.layout.user_heard_cardview, parent, false);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		CardView cardView = (CardView) view.findViewById(R.id.user_heard_cardview);
		UserSectionCard userSectionView = new UserSectionCard(context);
		cardView.setCard(userSectionView);
		userSectionView.setOnAuthinizeListener(new IUserLogin() {

			@Override
			public void onStartLogin(PLATFORM form) {

			}
		});
	}

}

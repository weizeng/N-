package com.nhaowan.mobile.base.cards;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.CardHeader.OnClickCardHeaderPopupMenuListener;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haha.frame.common.CommonUtils;
import com.haha.frame.widget.AutoImageView;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.bean.User;

public class UserSectionCard extends Card {
	private Context context;
	private AutoImageView userPhotoView;
	private TextView nameTextView;
	private TextView tagTextView;

	public UserSectionCard(Context context) {
		super(context, R.layout.user_header);
		this.context = context;
		init();
	}

	private void init() {
		CardHeader cardHeader = new CardHeader(context);
		cardHeader.setTitle(User.getInstance().getName());
		cardHeader.setPopupMenu(R.menu.menu_user_head_login, new OnClickCardHeaderPopupMenuListener() {

			@Override
			public void onMenuItemClick(BaseCard card, MenuItem item) {
				if (item.getItemId() == R.id.menu_head_item_sina) {
					if (listener != null) {
						listener.onStartLogin(IUserLogin.PLATFORM.SINA);
					}
				} else if (item.getItemId() == R.id.menu_head_item_tenc) {
					if (listener != null) {
						listener.onStartLogin(IUserLogin.PLATFORM.TENC);
					}
				} else if (item.getItemId() == R.id.menu_head_item_n) {
					if (listener != null) {
						listener.onStartLogin(IUserLogin.PLATFORM.N);
					}
				}
			}
		});
		addCardHeader(cardHeader);
		setOnClickListener(new OnCardClickListener(){

			@Override
			public void onClick(Card card, View view) {
				CommonUtils.showToast(context, "ccc");
			}
			
		});
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {
		super.setupInnerViewElements(parent, view);
		userPhotoView = (AutoImageView) view.findViewById(R.id.user_photo_imageview);
		nameTextView = (TextView) view.findViewById(R.id.user_name_textview);
		tagTextView = (TextView) view.findViewById(R.id.user_tag_textview);

	}

	public interface IUserLogin {
		enum PLATFORM {
			SINA, TENC, N
		}

		void onStartLogin(PLATFORM form);

	}

	private IUserLogin listener;

	public void setOnAuthinizeListener(IUserLogin listener) {
		this.listener = listener;
	}

}

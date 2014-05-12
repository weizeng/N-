/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.nhaowan.mobile.base.bean;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haha.frame.common.CommonUtils;
import com.nhaowan.mobile.R;

/**
 * This class provides a simple example of May Know Card
 *
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SystemCard extends Card {
	String title;
	String description;
	
    public SystemCard(Context context, String title, String des) {
        this(context, R.layout.carddemo_mayknow_inner_content);
        this.title = title;
        this.description = des;
    }

    public SystemCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {
        //Add Header
//        CardHeader header = new MayKnowCardHeader(getContext(), R.layout.carddemo_mayknow_inner_header);
//        header.setTitle(getContext().getString(R.string.may_know_card_title));
//        addCardHeader(header);
//        setShadow(false);

        //Add Thumbnail
//        CardThumbnail thumbnail = new CardThumbnail(getContext());
//        thumbnail.setUrlResource("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s72/new%2520profile%2520%25282%2529.jpg");
//        thumbnail.setErrorResource(R.drawable.ic_error_loadingsmall);
//        addCardThumbnail(thumbnail);

        OnCardClickListener clickListener = new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                //Do something
            	CommonUtils.showToast(mContext, "click");
            }
        };

        addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW,clickListener);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView titleTextView = (TextView) view.findViewById(R.id.carddemo_mayknow_main_inner_title);
        TextView subtitleTextView = (TextView) view.findViewById(R.id.carddemo_mayknow_main_inner_subtitle);
//        TextView add = (TextView) view.findViewById(R.id.carddemo_mayknow_main_inner_button);

        titleTextView.setText(title);
        subtitleTextView.setText(description);

        CardView cardView = getCardView();
        CardThumbnailView thumb = cardView.getInternalThumbnailLayout();
        if (thumb != null) {
            ViewGroup.LayoutParams lp = thumb.getLayoutParams();
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) lp).setMargins(25, 0, 0, 5);
            }
        }


    }

//    class MayKnowCardHeader extends CardHeader {
//
//        public MayKnowCardHeader(Context context, int innerLayout) {
//            super(context, innerLayout);
//        }
//
//        @Override
//        public void setupInnerViewElements(ViewGroup parent, View view) {
//            super.setupInnerViewElements(parent, view);
//
//            TextView t1 = (TextView) view.findViewById(R.id.carddemo_mayknow_main_inner_subtitle);
//            if (t1 != null)
//                t1.setText(getContext().getString(R.string.may_know_card_subtitle));
//        }
//    }
}

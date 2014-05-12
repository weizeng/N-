package com.nhaowan.mobile.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.bean.InnerMenu;

public class LeftMenuAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<InnerMenu> menus;

	public LeftMenuAdapter(Context mContext, ArrayList<InnerMenu> menus) {
		this.mContext = mContext;
		this.menus = menus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_listview_item, null);
			viewHolder.photoView = (ImageView) convertView
					.findViewById(R.id.left_menu_list_item_pic_imageview);
			viewHolder.title = (TextView) convertView.findViewById(R.id.left_menu_list_item_title_textview);
			viewHolder.pointView = (TextView) convertView.findViewById(R.id.left_menu_down_textview);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(menus.get(position).getTitle());

		if (menus.get(position).isChecked()) {
			viewHolder.photoView.setVisibility(View.VISIBLE);
		} else {
			viewHolder.photoView.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return menus.get(position).getId();
	}

	@Override
	public Object getItem(int position) {

		return menus.get(position);
	}

	@Override
	public int getCount() {
		return menus.size();
	}

	private class ViewHolder {
		TextView title;
		ImageView photoView;
		TextView pointView;
	}
}
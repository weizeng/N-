package com.nhaowan.mobile.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.bean.TopBean;
import com.nhaowan.mobile.base.bean.User;
import com.nhaowan.mobile.base.utils.Contants;
import com.nhaowan.mobile.base.utils.ImageManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TopGalleryAdapter extends BaseAdapter {
	private List<TopBean> dataList = new ArrayList<TopBean>();
	private LayoutInflater inflater;
	private Context mContext;

	public TopGalleryAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public int getCount() {
		// return dataList.size();
		return Integer.MAX_VALUE;
	}

	public TopBean getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		/**/
		if (position < 0) {
			position = position + dataList.size();
		}
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gallery_header_item, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.homepage_gallery_imageview);
			holder.titleView = (TextView) convertView.findViewById(R.id.homepage_gallery_title_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (dataList != null && dataList.size() == 0) {
			return convertView;
		}
		int mPosition = dataList.size() == 0 ? 0 : position % dataList.size();
		TopBean movieData = dataList.get(mPosition);
		holder.titleView.setText(movieData.getTitle());

		fetchImg(movieData, holder.imageView, true);
		return convertView;
	}

	private void fetchImg(final TopBean sro, final ImageView photoView, final boolean isMeasure) {
		final String url = sro.getThumb();
		if (!TextUtils.isEmpty(url) && photoView != null) {
			String path = Contants.PATH_SDCARD_FILES + url.hashCode();
			ImageLoader.getInstance().displayImage(url, photoView);

		} else {
			photoView.setImageBitmap(null);
		}
		photoView.setTag(url);
	}

	final static class ViewHolder {
		public ImageView imageView;
		public TextView titleView;
	}

	public List<TopBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<TopBean> dd) {
		if (dd == null || dd.size() == 0) {
			return;
		}
		dataList.clear();
		dataList.addAll(dd);
		notifyDataSetChanged();
	}
}

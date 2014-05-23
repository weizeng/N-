package com.nhaowan.mobile.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.net.IFetcher;
import com.leo.utils.DeviceUtils;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.response.UpdateAppResponse;
import com.nhaowan.mobile.ui.logic.AppSettingManager;
import com.nhaowan.mobile.ui.preference.UserPreference;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SettingFragment extends PreferenceFragment {
	private final String TAG = SettingFragment.class.getSimpleName();
	private UserPreference userPreference;
	private Preference versionCheckPreference;
	private Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting_layout, container, false);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting_preferences);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		checkCache();
		checkVersion();
	}

	private void checkVersion() {
		// check version
		versionCheckPreference = findPreference("setting_key_version_preference");
		versionCheckPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				return versonCheck();
			}
		});

		PackageInfo pi = DeviceUtils.getCurrentPacketInfo(getActivity());
		if (pi != null) {
			versionCheckPreference.setTitle("N+ 版本是: " + pi.versionName + "");
			versionCheckPreference.setSummary("");
		}
	}

	private void checkCache() {
		final Preference cleanPreference = findPreference("setting_key_clean_preference");
		cleanPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				// clean my sys
				cleanApp();
				cleanPreference.setTitle("清理缓存, 当前有" + AppSettingManager.getCacheMemory());
				return true;
			}
		});

		cleanPreference.setTitle("清理缓存, 当前有" + AppSettingManager.getCacheMemory());
	}

	protected boolean versonCheck() {

		if (versionCheckPreference.getIntent() != null) {
			startActivity(versionCheckPreference.getIntent());
		} else {
			//
			return AppSettingManager.checkVersion(getActivity(), new IFetcher<UpdateAppResponse>() {

				@Override
				public void onFetcherStart() {
				}

				@Override
				public void onFetcherSuccess(UpdateAppResponse t) {
					if (!TextUtils.isEmpty(t.getAppurl())) {
						versionCheckPreference.setTitle("请及时下载更新，有新版本啦：" + t.getVersion());
						versionCheckPreference.setSummary(t.getChangelog());

						Uri uri = Uri.parse(t.getAppurl());
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						versionCheckPreference.setIntent(intent);
					} else {
						// CommonUtils.showToast(getActivity(), "您已经是最新版啦");
						Crouton.makeText((Activity) mContext, "您已经是最新版啦", Style.CONFIRM);
					}
				}

				@Override
				public void onFetcherFailed(String error) {
					Crouton.makeText((Activity) mContext, "检查不到，是否网络不可用呢", Style.CONFIRM);
				}

			});
		}
		return true;

	}

	protected boolean cleanApp() {
//		if (FileUtils.delFiles(new File(Contants.PATH_SDCARD_ROOT))) {
			ImageLoader.getInstance().clearDiskCache();
			ImageLoader.getInstance().clearMemoryCache();
			return true;
//		}
	}

}

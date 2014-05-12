package com.nhaowan.mobile.ui.fragment;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaowan.mobile.R;
import com.nhaowan.mobile.ui.preference.UserPreference;

public class SettingFragment extends PreferenceFragment {
	private UserPreference userPreference;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting_layout, container, false);
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
		findPreference("setting_key_clean_preference");
		findPreference("setting_key_version_preference");
		
//		 UserPreference = findPreference("setting_key_version_preference"); 
	}
 
}

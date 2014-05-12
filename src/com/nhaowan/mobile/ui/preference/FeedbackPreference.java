package com.nhaowan.mobile.ui.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nhaowan.mobile.R;

public class FeedbackPreference extends Preference {
	private EditText emailEditText;
	private EditText pwdEditText;
	private Context context;

	public FeedbackPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public FeedbackPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public FeedbackPreference(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		return LayoutInflater.from(getContext()).inflate(R.layout.fragment_preference_setting_feedback, parent, false);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		emailEditText = (EditText) view.findViewById(R.id.editEmail);
		pwdEditText = (EditText) view.findViewById(R.id.editPassword);

	}
}

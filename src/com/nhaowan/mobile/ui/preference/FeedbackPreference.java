package com.nhaowan.mobile.ui.preference;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.leo.common.CommonUtils;
import com.leo.net.AsyncHttpManager;
import com.leo.net.AsyncHttpResponseHandler;
import com.nhaowan.mobile.R;
import com.nhaowan.mobile.base.utils.Contants;

public class FeedbackPreference extends Preference {
	private EditText emailEditText;
	private EditText mobileEditText;
	private Button submitImageButton;
	private Context mContext;
	private ProgressBar progressBar;

	public FeedbackPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public FeedbackPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public FeedbackPreference(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		return LayoutInflater.from(getContext()).inflate(R.layout.fragment_preference_setting_feedback,
				parent, false);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		emailEditText = (EditText) view.findViewById(R.id.preference_feedback_mobile_editText);
		mobileEditText = (EditText) view.findViewById(R.id.preference_feedback_mobile_editText);
		submitImageButton = (Button) view.findViewById(R.id.preference_feedback_btn);
		progressBar = (ProgressBar) view.findViewById(R.id.preference_feedback_mobile_progressBar);
		
		
		submitImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
				String content = emailEditText.getText().toString();
				if (TextUtils.isEmpty(content)) {
					CommonUtils.showToast(mContext, "您还是说点什么吧");
				} else {
					enableWidgets(false);
					
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("email", emailEditText.getText().toString());
					params.put("content", content);
					AsyncHttpManager.getInstance().post(mContext, "/app/feedback", params,
							Contants.SERVICE_ROOT + "/app/feedback", new AsyncHttpResponseHandler() {
								public void onSuccess(int statusCode, org.apache.http.Header[] headers,
										String content) {
									enableWidgets(true);

									try {
										if (!TextUtils.isEmpty(content)) {
											JSONObject jsonObject = new JSONObject(content);
											if (0 == jsonObject.getInt("ret")) {
												CommonUtils
														.showToast(mContext, "反馈成功啦，感谢您的意见，N好玩，绝对好玩的游戏推荐！");
											}
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								
							});
				}

			}
		});
	}
	
	private void enableWidgets(boolean value) {
		progressBar.setVisibility(value? View.VISIBLE : View.INVISIBLE);
		emailEditText.setEnabled(value);
		mobileEditText.setEnabled(value);
		submitImageButton.setEnabled(value);
	};
}

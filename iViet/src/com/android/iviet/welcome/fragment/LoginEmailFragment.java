package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.utils.CommonConstants;
import com.android.iviet.utils.CommonUtils;
import com.android.iviet.utils.PreferenceUtil;
import com.android.iviet.welcome.callbacks.BaseInterface;

public class LoginEmailFragment extends SwipeToCloseFragent implements
		OnClickListener {
	private EditText mEdtEmail;
	private EditText mEdtPass;
	private TextView mTvForgetPass;
	private Button mBtnLogin;
	// private GestureDetector mGestureDetector;
	private static BaseInterface mBaseInterface;

	public static LoginEmailFragment createLoginEmailFragment(
			BaseInterface baseInterface) {
		mBaseInterface = baseInterface;
		return new LoginEmailFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.login_email_layout,
				container, false);
		mEdtEmail = (EditText) rootView.findViewById(R.id.login_email_edt_mail);
		mEdtPass = (EditText) rootView.findViewById(R.id.login_email_edt_pass);
		mTvForgetPass = (TextView) rootView
				.findViewById(R.id.login_tv_forget_pass);
		mBtnLogin = (Button) rootView.findViewById(R.id.login_email_btn_login);
		mBtnLogin.setOnClickListener(this);
		mTvForgetPass.setOnClickListener(this);
		String email = PreferenceUtil.getPreference(getActivity(),
				CommonConstants.LOGIN_EMAIL_KEY, "");
		if (!email.equalsIgnoreCase("")) {
			mEdtEmail.setText(email);
		}
		// mGestureDetector = new GestureDetector(getActivity(), new Gesture());
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (mBaseInterface == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.login_email_btn_login:
			checkLogin();
			break;
		case R.id.login_tv_forget_pass:
			mBaseInterface.onClickInFragment(getClass().getName(),
					R.id.login_tv_forget_pass);
			break;
		default:
			break;
		}
	}

	private void checkLogin() {
		boolean isValid = !CommonUtils.isEmptyEditext(mEdtEmail)
				&& CommonUtils.isEmailValid(mEdtEmail.getText().toString())
				&& !CommonUtils.isEmptyEditext(mEdtPass);
		if (!isValid) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.email_pass_khong_hop_le));
			return;
		}
		if (!CommonUtils.isWifiOn(getActivity())
				&& !CommonUtils.is3GOn(getActivity())) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.kiem_tra_ket_noi_internet));
			return;
		}
		PreferenceUtil
				.setPreference(getActivity(), CommonConstants.LOGIN_EMAIL_KEY,
						mEdtEmail.getText().toString());
		mBaseInterface.onClickInFragment(getClass().getName(),
				R.id.login_email_btn_login);
	}

	private class Gesture extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (velocityX > 0 && getActivity() instanceof BaseFragmentActivity) {
				((BaseFragmentActivity) getActivity()).onBackPressed();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

}

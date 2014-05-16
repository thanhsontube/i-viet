package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.utils.CommonConstants;
import com.android.iviet.utils.CommonUtils;
import com.android.iviet.utils.PreferenceUtil;
import com.android.iviet.welcome.callbacks.BaseInterface;

public class LoginEmailFragment extends Fragment implements OnTouchListener {
	private EditText mEdtEmail;
	private EditText mEdtPass;
	private GestureDetector mGestureDetector;
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
		String email = PreferenceUtil.getPreference(getActivity(),
				CommonConstants.LOGIN_EMAIL_KEY, "");
		if (!email.equalsIgnoreCase("")) {
			mEdtEmail.setText(email);
		}
		rootView.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(getActivity(), mGestureListener);
		return rootView;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
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
		boolean isValid = CommonUtils.isEmptyEditext(mEdtEmail)
				|| CommonUtils.isEmailValid(mEdtEmail.getText().toString())
				|| CommonUtils.isEmptyEditext(mEdtPass);
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

	private GestureDetector.SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		public boolean onScroll(android.view.MotionEvent e1,
				android.view.MotionEvent e2, float distanceX, float distanceY) {
			Log.e("tag", "distanceX "+distanceX);
			if (distanceX > 0 && getActivity() instanceof BaseFragmentActivity) {
				((BaseFragmentActivity) getActivity()).onBackPressed();
			}
			return false;
		};
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		mGestureDetector.onTouchEvent(event);
		return false;
	}
}

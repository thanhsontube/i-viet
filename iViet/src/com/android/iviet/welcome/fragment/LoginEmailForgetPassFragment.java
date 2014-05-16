package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class LoginEmailForgetPassFragment extends Fragment implements
		OnTouchListener {
	private EditText mEdtEmail;
	private GestureDetector mGestureDetector;
	private static BaseInterface mBaseInterface;

	public static LoginEmailForgetPassFragment createLoginEmailForgetPassFragment(
			BaseInterface baseInterface) {
		mBaseInterface = baseInterface;
		return new LoginEmailForgetPassFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.login_email_forget_pass_layout, container, false);
		mEdtEmail = (EditText) rootView
				.findViewById(R.id.login_email_edt_forgetpass_email);
		String email = PreferenceUtil.getPreference(getActivity(),
				CommonConstants.LOGIN_EMAIL_KEY, "");
		if (!email.equalsIgnoreCase("")) {
			mEdtEmail.setText(email);
		}
		rootView.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(getActivity(), new Gesture());
		return rootView;
	}

	public void onClick(View v) {
		if (mBaseInterface == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.login_email_btn_get_pass:
			checkEmailAddress();
			break;
		default:
			break;
		}
	}

	private void checkEmailAddress() {
		boolean isValid = !CommonUtils.isEmptyEditext(mEdtEmail)
				&& CommonUtils.isEmailValid(mEdtEmail.getText().toString());
		if (!isValid) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.email_khong_hop_le));
			return;
		}
		if (!CommonUtils.isWifiOn(getActivity())
				&& !CommonUtils.is3GOn(getActivity())) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.kiem_tra_ket_noi_internet));
			return;
		}
		mBaseInterface.onClickInFragment(getClass().getName(),
				R.id.login_email_btn_get_pass);
	}

	private class Gesture extends SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (velocityX > 0 && getActivity() instanceof BaseFragmentActivity) {
				((BaseFragmentActivity) getActivity()).onBackPressed();
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}
}

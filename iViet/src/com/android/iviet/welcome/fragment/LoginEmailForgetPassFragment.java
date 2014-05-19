package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.iviet.R;
import com.android.iviet.utils.CommonUtils;
import com.android.iviet.welcome.callbacks.BaseInterface;

public class LoginEmailForgetPassFragment extends SwipeToCloseFragent implements OnClickListener {
	private EditText mEdtEmail;
	private Button mBtnGetPass;
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
		mBtnGetPass = (Button) rootView.findViewById(R.id.login_email_btn_get_pass);
		mBtnGetPass.setOnClickListener(this);
//		String email = PreferenceUtil.getPreference(getActivity(),
//				CommonConstants.LOGIN_EMAIL_KEY, "");
//		if (!email.equalsIgnoreCase("")) {
//			mEdtEmail.setText(email);
//		}
		return rootView;
	}
	@Override
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

}

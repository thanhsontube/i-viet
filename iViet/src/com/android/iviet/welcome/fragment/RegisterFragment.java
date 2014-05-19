package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.utils.CommonUtils;
import com.android.iviet.welcome.callbacks.BaseInterface;

public class RegisterFragment extends SwipeToCloseFragent implements
		OnClickListener {
	private EditText mEdtUser;
	private EditText mEdtEmail;
	private EditText mEdtPass;
	private EditText mEdtConfirmPass;
	private TextView mTvIAgree;
	private CheckBox mCBIAgree;
	private Button mBtnRegister;
	// private GestureDetector mGestureDetector;
	private static BaseInterface mBaseInterface;

	public static RegisterFragment createRegisterFragment(
			BaseInterface baseInterface) {
		mBaseInterface = baseInterface;
		return new RegisterFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.register_layout, container,
				false);
		mEdtUser = (EditText) rootView.findViewById(R.id.register_edt_username);
		mEdtEmail = (EditText) rootView.findViewById(R.id.register_edt_mail);
		mEdtPass = (EditText) rootView.findViewById(R.id.register_edt_pass);
		mEdtConfirmPass = (EditText) rootView
				.findViewById(R.id.register_edt_confirm_pass);
		mTvIAgree = (TextView) rootView.findViewById(R.id.register_tv_agree);
		mCBIAgree = (CheckBox) rootView.findViewById(R.id.register_cb_agree);
		mBtnRegister = (Button) rootView
				.findViewById(R.id.register_btn_register);
		mBtnRegister.setOnClickListener(this);
		mTvIAgree.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (mBaseInterface == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.register_btn_register:
			checkRegister();
			break;
		case R.id.register_tv_agree:
			mBaseInterface.onClickInFragment(getClass().getName(),
					R.id.register_tv_agree);
			break;
		default:
			break;
		}
	}

	private void checkRegister() {
		if (CommonUtils.isEmptyEditext(mEdtConfirmPass)
				|| CommonUtils.isEmptyEditext(mEdtEmail)
				|| CommonUtils.isEmptyEditext(mEdtPass)
				|| CommonUtils.isEmptyEditext(mEdtUser)) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.chua_nhap_du_thong_tin));
			return;
		}
		if (!CommonUtils.isEmailValid(mEdtEmail.getText().toString())) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.email_khong_hop_le));
			return;
		}
		if (!mEdtConfirmPass.getText().toString()
				.equals(mEdtPass.getText().toString())) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.mat_khau_khong_khop));
			return;
		}
		if (!mCBIAgree.isChecked()) {
			CommonUtils.showInfoDialog(getActivity(),
					getActivity().getString(R.string.loi), getActivity()
							.getString(R.string.chua_dong_y));
			return;
		}
		mBaseInterface.onClickInFragment(getClass().getName(),
				R.id.register_btn_register);
	}

}

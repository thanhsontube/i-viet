package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.iviet.R;
import com.android.iviet.welcome.callbacks.BaseInterface;

public class PolicyFragment extends SwipeToCloseFragent implements
		OnClickListener {
	private ImageButton mBtnBack;
	// private GestureDetector mGestureDetector;
	private static BaseInterface mBaseInterface;

	public static PolicyFragment createPolicyFragment(
			BaseInterface baseInterface) {
		mBaseInterface = baseInterface;
		return new PolicyFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.register_policy_layout, container,
				false);
		mBtnBack = (ImageButton) rootView
				.findViewById(R.id.register_policy_btn_back);
		mBtnBack.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (mBaseInterface == null) {
			return;
		}
		switch (v.getId()) {
		case R.id.register_policy_btn_back:
			goBack();
			break;
		default:
			break;
		}
	}

	private void goBack() {
		mBaseInterface.onClickInFragment(getClass().getName(),
				R.id.register_policy_btn_back);
	}

}

package com.android.iviet.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iviet.base.BaseWebViewFragment;
import com.android.iviet.utils.ActionBarUtils;

public class WriteQuestionFragment extends BaseWebViewFragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		ActionBarUtils.setTitle(actionBar, "Đăng câu hỏi");
		ActionBarUtils.hideChat(actionBar, true);
		ActionBarUtils.hideDot(actionBar, true);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		 mTop.setVisibility(View.GONE);
	}
}

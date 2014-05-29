package com.android.iviet.webview;

import android.view.View;

import com.android.iviet.base.BaseWebViewFragment;

public class UserGuideFragment extends BaseWebViewFragment {
	@Override
    protected String generateTitle() {
	    return "Hướng dẫn sử dụng";
    }

	@Override
    protected boolean isShowSendMenuItem() {
	    return false;
    }

	@Override
    protected int isShowFastTop() {
	    return View.VISIBLE;
    }

	@Override
    protected int isShowAddImage() {
	    return View.GONE;
    }
	
	
}

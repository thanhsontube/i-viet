package com.android.iviet.webview;

import android.view.View;

import com.android.iviet.R;
import com.android.iviet.base.BaseWebViewFragment;

public class PolicyFragment extends BaseWebViewFragment {
	@Override
    protected String generateTitle() {
	    return getString(R.string.terms);
    }

	@Override
    protected boolean isShowSendMenuItem() {
	    return false;
    }

	@Override
    protected int isShowFastTop() {
	    return View.GONE;
    }

	@Override
    protected int isShowAddImage() {
	    return View.GONE;
    }
	
	
}

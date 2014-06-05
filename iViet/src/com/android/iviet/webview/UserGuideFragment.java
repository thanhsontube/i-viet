package com.android.iviet.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;

import com.android.iviet.R;
import com.android.iviet.base.BaseWebViewFragment;

public class UserGuideFragment extends BaseWebViewFragment {
	@Override
    protected String generateTitle() {
	    return getString(R.string.guide);
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View v =  super.onCreateView(inflater, container, savedInstanceState);
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
	    webview.getSettings().setLoadWithOverviewMode(true);
	    webview.getSettings().setUseWideViewPort(true);
	    return v;
	}
	
	
}

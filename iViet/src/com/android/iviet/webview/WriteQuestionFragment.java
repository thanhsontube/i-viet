package com.android.iviet.webview;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.iviet.base.BaseWebViewFragment;

public class WriteQuestionFragment extends BaseWebViewFragment {
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
//		 mTop.setVisibility(View.GONE);
	}

	@Override
    protected String generateTitle() {
	    return "Đăng câu hỏi";
    }

	@Override
    protected boolean isShowSendMenuItem() {
	    return true;
    }

	@Override
    protected int isShowFastTop() {
	    return View.GONE;
    }

	@Override
    protected int isShowAddImage() {
	    return View.VISIBLE;
    }
}

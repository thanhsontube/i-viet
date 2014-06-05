package com.android.iviet.main.fragment;

import com.android.iviet.MsConst;
import com.android.iviet.R;

public class Answer2Fragment extends BaseTopFragment {

	@Override
	public int initType() {
		return MsConst.TYPE_NEWEST;
	}

	@Override
	protected String generateTitle() {
		return getString(R.string.title_answer);
	}

}

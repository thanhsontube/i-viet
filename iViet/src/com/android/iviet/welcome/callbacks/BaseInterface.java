package com.android.iviet.welcome.callbacks;

import android.support.v4.app.Fragment;

public interface BaseInterface {
	public void onClickInFragment(String fragmentName, int id);
	
	void onClickFacebook (Fragment f);
	void onFbPrepare (Fragment f);
	void onFbGetInfoSuccess (Fragment f);
	void onFbGetInfoFail (Fragment f);
}

package com.android.iviet.user;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class UserProfileFragment extends Fragment {
	
	private static final String TAG = "UserProfileFragment";
	private ActionBar mActionBar;
	FilterLog log = new FilterLog(TAG);
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
		// Set up the action bar.
		mActionBar = getActivity().getActionBar();
		ActionBarUtils.setTitle(mActionBar, "Thông tin");
		return rootView;
	}
}

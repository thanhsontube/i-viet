package com.android.iviet.newfeed;

import com.android.iviet.R;
import com.android.iviet.base.OnBackPressListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewFeedFragment extends Fragment implements OnBackPressListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.new_feed_fragmnt, container, false);
		return v;
	}

	@Override
    public boolean onBackPress() {
	    return false;
    }
}

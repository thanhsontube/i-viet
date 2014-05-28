package com.android.iviet.help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iviet.R;
import com.android.iviet.about.BaseFragment;

public class HelpFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.help_fragment, container, false);
		return v;
	}

	@Override
    protected String generateTitle() {
	    return "Hướng dẫn sử dụng";
    }
}

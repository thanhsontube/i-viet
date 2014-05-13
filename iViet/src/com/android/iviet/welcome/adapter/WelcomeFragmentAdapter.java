package com.android.iviet.welcome.adapter;

import com.android.iviet.welcome.fragment.SlideFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WelcomeFragmentAdapter extends FragmentPagerAdapter {

	private static final int WELCOME_PAGES = 5;

	public WelcomeFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		SlideFragment slideFragment = new SlideFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(SlideFragment.SLIDE_ORDER, arg0);
		slideFragment.setArguments(bundle);
		return slideFragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return WELCOME_PAGES;
	}

}

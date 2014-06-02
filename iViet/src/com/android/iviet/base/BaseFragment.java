package com.android.iviet.base;

import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

abstract public class BaseFragment extends Fragment {
	public MenuItem menuTransparent;
	
	protected abstract String generateTitle();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		if (!TextUtils.isEmpty(generateTitle())){
			ActionBarUtils.setTitle(getActivity().getActionBar(), generateTitle());
		}
	
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menuTransparent = menu.add(Menu.NONE, 8, Menu.NONE, "Chat123");
		menuTransparent.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuTransparent.setIcon(R.drawable.shape_icon);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
	    super.onHiddenChanged(hidden);
	    ActionBarUtils.setTitle(getActivity().getActionBar(), generateTitle());
	}
}

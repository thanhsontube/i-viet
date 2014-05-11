package com.android.iviet.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.iviet.R;
import com.android.iviet.base.AbsFragmentActivity;
import com.android.iviet.main.fragment.MainFragment;

public class MainActivity extends AbsFragmentActivity{

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		MainFragment topFragment = new MainFragment();
		return topFragment;
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.main_ll_main;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}

}

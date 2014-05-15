package com.android.iviet.main;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.MainFragment;
import com.android.iviet.main.fragment.MainFragment.IMainFragmentListener;
import com.android.iviet.main.fragment.Top1Fragment;
import com.android.iviet.main.fragment.Top1Fragment.ITop1FragmentListener;
import com.android.iviet.utils.FilterLog;

public class MainActivity extends BaseFragmentActivity implements ITop1FragmentListener, IMainFragmentListener{
	
	private static final String TAG = "MainActivity";
	FilterLog log = new FilterLog(TAG);

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
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.actionbar_custom, null);

		actionBar.setCustomView(v);
	}

	@Override
    public void onIMainFragmentStart(MainFragment f, int i) {
	    // TODO Auto-generated method stub
		log.d("NECVN>>>" + "onIMainFragmentStart");
	    
    }

	@Override
    public void onTop1AvatarClicked(Top1Fragment f,MainDto dto) {
		log.d("NECVN>>>" + "onTop1AvatarClicked");
	    
    }

	@Override
    public void onTop1ContentClicked(Top1Fragment f,MainDto dto) {
		log.d("NECVN>>>" + "onTop1ContentClicked");
	    
    }

}

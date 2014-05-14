package com.android.iviet.main.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iviet.MsConst;
import com.android.iviet.R;
import com.android.iviet.base.OnBackPressListener;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.Top1Fragment.ITop1FragmentListener;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class MainFragment extends Fragment implements OnPageChangeListener, OnBackPressListener, ITop1FragmentListener 
{
	
	private static final String TAG = "MainFragment";
	private MainPagerAdapter mMainPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	FilterLog log = new FilterLog(TAG);
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = (ViewGroup)inflater.inflate(R.layout.main_fragment, container, false);
		
		// Set up the action bar.
		mActionBar = getActivity().getActionBar();
		
		mMainPagerAdapter = new MainPagerAdapter(getFragmentManager(), getActivity());
		
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mMainPagerAdapter);
		mViewPager.setCurrentItem(MsConst.INDEX_TOP);
		mViewPager.setOnPageChangeListener(this);
		ActionBarUtils.setTitle(mActionBar, getApplicationTitle(MsConst.INDEX_TOP));
		
		return rootView;
	}
	
	public CharSequence getApplicationTitle(int position) {
		return mMainPagerAdapter.getPageTitle(position);
	}
	
	public void changeViewPager(int i){
		mViewPager.setCurrentItem(i);
	}
	
	public boolean resetPagePosition() {
		if (mViewPager.getCurrentItem() != MsConst.INDEX_TOP) {
			changeViewPager(MsConst.INDEX_TOP);
			return true;
		}
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mViewPager.invalidate();
	}
	
	

	@Override
	public boolean onBackPress() {
		return resetPagePosition();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
//		for (int i = 0; i < mMainPagerAdapter.getCount(); i++) {
//			final Fragment f = mMainPagerAdapter.getFragment(mViewPager, i);
//			if (f != null && f instanceof MainFragmentListener) {
//				final MainFragmentListener listener = (MainFragmentListener) f;
//				if (position == i) {
//					listener.onMainFragmentPageSelected(this, f);
//				} else {
//					listener.onMainFragmentPageDeSelected(this, f);
//				}
//			}
//		}
		ActionBarUtils.setTitle(mActionBar, getApplicationTitle(position));
		
	}
	
	//adapter
	
//	public class MainPagerAdapter extends PagerAdapter {
//		
//		Context mContext;
//		private Fragment mPrimaryFragment;
//
//		public MainPagerAdapter(FragmentManager fm, Context context) {
//			this.mContext = context;
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//		
//	}
	
	private class MainPagerAdapter extends FragmentPagerAdapter {

		public MainPagerAdapter(FragmentManager fm, Context context) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return new Top1Fragment();
			case 1:
				return new Top2Fragment();
			case 2:
				return new Top3Fragment();
			
			}
			
			return null;

		}

		@Override
		public int getCount() {
			return 3;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "DE CAO";
			case 1:
				return "MOI NHAT";
			case 2:
				return "CAU HOI CUA BAN";
			default:
				break;
			}
			return "iViet";
		}

	}

	@Override
	public void onTop1AvatarClicked(MainDto dto) {
		// TODO Auto-generated method stub
		log.d("NECVN>>> " + "onTop1AvatarClicked");
		
	}

	@Override
	public void onTop1ContentClicked(MainDto dto) {
		// TODO Auto-generated method stub
		log.d("NECVN>>> " + "onTop1ContentClicked");
		
	}
}

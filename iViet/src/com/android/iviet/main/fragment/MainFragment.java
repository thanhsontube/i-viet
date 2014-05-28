package com.android.iviet.main.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.iviet.MsConst;
import com.android.iviet.R;
import com.android.iviet.base.OnBackPressListener;
import com.android.iviet.main.adapter.MyFragmentPagerAdapter;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class MainFragment extends Fragment implements OnPageChangeListener, OnBackPressListener {

	private static final String TAG = "MainFragment";
	private MainPagerAdapter mMainPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar mActionBar;
	FilterLog log = new FilterLog(TAG);

	private String[] pageTitle = {"Đề cao", "Mới nhất", "Câu hỏi của bạn"}; 

	private IMainFragmentListener listener;

	public static interface IMainFragmentListener {
		void onIMainFragmentStart(MainFragment f, int i);
		void onMainFragmentPageSelected(MainFragment main, Fragment selected);
		void onMainFragmentPageDeSelected(MainFragment main, Fragment selected);
		void onMainFragmentMenuChatSelected(MainFragment main);
		// public void onIMainFragmentSwipe(MainFragment f, int i, CharSequence title);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof IMainFragmentListener) {
			listener = (IMainFragmentListener) activity;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.main_fragment, container, false);

		// Set up the action bar.
		mActionBar = getActivity().getActionBar();

		mMainPagerAdapter = new MainPagerAdapter(getFragmentManager(), getActivity());

		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mMainPagerAdapter);
		mViewPager.setCurrentItem(MsConst.INDEX_NEWEST);
		mViewPager.setOnPageChangeListener(this);

		Bundle args = getArguments();
		if (args != null && args.containsKey(MsConst.BUNDLE_TRANSITION)) {
			int pos = args.getInt(MsConst.BUNDLE_TRANSITION);
			mViewPager.setCurrentItem(pos);
			ActionBarUtils.update(mActionBar, pos, getApplicationTitle(pos));
		} else {
			mViewPager.setCurrentItem(MsConst.INDEX_NEWEST);
			ActionBarUtils.update(mActionBar, MsConst.INDEX_NEWEST, getApplicationTitle(MsConst.INDEX_NEWEST));
		}

		if (listener != null) {
			listener.onIMainFragmentStart(MainFragment.this, 10);
		}

		return rootView;
	}

	public CharSequence getApplicationTitle(int position) {
		return mMainPagerAdapter.getPageTitle(position);
	}

	public void changeViewPager(int i) { 
		mViewPager.setCurrentItem(i);
	}

	public boolean resetPagePosition() {
		if (mViewPager.getCurrentItem() != MsConst.INDEX_NEWEST) {
			changeViewPager(MsConst.INDEX_NEWEST);
			return true;
		}
		return false;
	}
	
	public void update() {
		ActionBarUtils.hideChat(getActivity().getActionBar(), false);
		ActionBarUtils.hideDot(getActivity().getActionBar(), false);
		int position = mViewPager.getCurrentItem();
		ActionBarUtils.update(getActivity().getActionBar(), position, getApplicationTitle(position));
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
		for (int i = 0; i < mMainPagerAdapter.getCount(); i++) {
			final Fragment f = mMainPagerAdapter.getFragment(mViewPager, i);
			if (f != null && f instanceof IMainFragmentListener) {
				final IMainFragmentListener listener = (IMainFragmentListener) f;
				if (position == i) {
					listener.onMainFragmentPageSelected(this, f);
				} else {
					listener.onMainFragmentPageDeSelected(this, f);
				}
			}
		}
		ActionBarUtils.update(mActionBar, position, getApplicationTitle(position));

	}

	// adapter

		class MainPagerAdapter extends MyFragmentPagerAdapter {
		
		Context mContext;
		private Fragment mPrimaryFragment;

		public MainPagerAdapter(FragmentManager fm, Context context) {
			super(fm);
			this.mContext = context;
		}
		
		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
			mPrimaryFragment = (Fragment) object;
		}
		
		public Fragment getCurrentFragment() {
			return mPrimaryFragment;
		}

		@Override
		public boolean isFragmentReusable(Fragment f, int position) {
			return true;
		}

		@Override
		public Fragment getItem(int position) {

			// Genre
			if(position == MsConst.INDEX_NEWEST){
				Fragment newestFragment = new Top1Fragment();
				return newestFragment;
			}
			// Top
			if(position == MsConst.INDEX_FEATURED){
				Fragment newestFragment = new Top1Fragment();
				return newestFragment;
			}
			
			// New
			if(position == MsConst.INDEX_YOUR_QUESTION){
				Fragment newestFragment = new Top1Fragment();
				return newestFragment;
			}
			throw new IllegalStateException();
		}
		
		@Override
		public int getCount() {
			return pageTitle.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return pageTitle[position];
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public CharSequence getPageName(int position) {
			return pageTitle[position];
		}
	}
		
		@Override
		public void onHiddenChanged(boolean hidden) {
			super.onHiddenChanged(hidden);
			if (hidden) {
				setMenuVisibility(false);
				if (mMainPagerAdapter != null && mMainPagerAdapter.getCurrentFragment() != null) {
					mMainPagerAdapter.getCurrentFragment().setMenuVisibility(false);
				}
			} else {
				setMenuVisibility(true);
				if (mMainPagerAdapter != null && mMainPagerAdapter.getCurrentFragment() != null) {
					mMainPagerAdapter.getCurrentFragment().setMenuVisibility(true);
				}
				if (mActionBar != null) {
//					ActionBarUtils.setTitle(mActionBar, getApplicationTitle(mViewPager.getCurrentItem()));
//					mActionBar.setTitle(getApplicationTitle(mViewPager.getCurrentItem()));
					update();
				}
			}
		}
		private MenuItem menuChat;
		private boolean isMenuChat = true;
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		    // TODO Auto-generated method stub
		    super.onCreateOptionsMenu(menu, inflater);
		    menuChat = menu.add(Menu.NONE, 1, Menu.NONE, "Chat");
			menuChat.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			menuChat.setIcon(R.drawable.chat_select);
		}
		
		@Override
		public void onPrepareOptionsMenu(Menu menu) {
		    // TODO Auto-generated method stub
		    super.onPrepareOptionsMenu(menu);
		    menuChat.setVisible(isMenuChat);
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // TODO Auto-generated method stub
			switch (item.getItemId()) {
			case 1:
				Toast.makeText(getActivity(), "menu Chat >>>", Toast.LENGTH_SHORT).show();
				if (listener != null) {
					listener.onMainFragmentMenuChatSelected(this);
				}
				break;

			default:
				break;
			}
		    return super.onOptionsItemSelected(item);
		}

}

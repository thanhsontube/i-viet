package com.android.iviet.welcome.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.iviet.R;
import com.android.iviet.welcome.adapter.WelcomeFragmentAdapter;
import com.android.iviet.welcome.lib.CirclePageIndicator;

public class WelcomeFragment extends Fragment implements OnPageChangeListener {
	private static final int TIMEOUT_SWITCH_PAGE = 7000;
	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mWelcomePager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mWelcomePagerAdapter;
	private View mWelcomeBackground;
	private AutoSwitchPageTask mAutoSwitchPageTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.welcome_fragment, container, false);
		mWelcomeBackground = getActivity().findViewById(
				R.id.welcome_v_background);
		mWelcomePager = (ViewPager) rootView.findViewById(R.id.welcome_pager);
		mWelcomePagerAdapter = new WelcomeFragmentAdapter(getActivity()
				.getSupportFragmentManager());
		mWelcomePager.setAdapter(mWelcomePagerAdapter);
		CirclePageIndicator indicator = (CirclePageIndicator) rootView
				.findViewById(R.id.welcome_pager_indicator);
		indicator.setViewPager(mWelcomePager);
		indicator.setSnap(true);
		indicator.setOnPageChangeListener(this);
		mAutoSwitchPageTask = new AutoSwitchPageTask();
		mAutoSwitchPageTask.execute();
		return rootView;

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (arg0 == ViewPager.SCROLL_STATE_DRAGGING) {
			mAutoSwitchPageTask.cancel(true);
		} else if (arg0 == ViewPager.SCROLL_STATE_SETTLING) {
			mAutoSwitchPageTask.cancel(true);
			mAutoSwitchPageTask = new AutoSwitchPageTask();
			mAutoSwitchPageTask.execute();
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int arg0) {
		Resources resources = getResources();
		Drawable drawable = resources.getDrawable(resources.getIdentifier(
				"ic_iviet_background_" + (arg0 + 1), "drawable", getActivity()
						.getPackageName()));
		mWelcomeBackground.setBackgroundDrawable(drawable);
	}

	private class AutoSwitchPageTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(TIMEOUT_SWITCH_PAGE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			if (!isCancelled()) {
				int page = mWelcomePager.getCurrentItem();
				int count = mWelcomePagerAdapter.getCount();
				if (count == 0) {
					return;
				}
				page = (page + 1) % mWelcomePagerAdapter.getCount();
				mWelcomePager.setCurrentItem(page, true);
			}
			super.onPostExecute(result);
		}
	}

}

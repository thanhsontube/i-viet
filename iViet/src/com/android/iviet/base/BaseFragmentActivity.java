package com.android.iviet.base;

import java.util.Collection;
import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.android.iviet.utils.FilterLog;

abstract public class BaseFragmentActivity extends FragmentActivity implements
		OnBackStackChangedListener {

	protected static final String FRAGMENT_KEY = "fragment-key";
	protected static final String SAVE_KEY_STACK = "tag_stack";
	private static final String TAG = "AbsFragmentActivity";
	FilterLog log = new FilterLog(TAG);
	

	abstract protected Fragment createFragmentMain(Bundle savedInstanceState);

	abstract protected int getFragmentContentId();

	protected final Stack<String> mFragmentTagStack = new Stack<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		log.d("NECVN>>>" + "onCreate savedInstanceState:" + savedInstanceState);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(getFragmentContentId(),
							createFragmentMain(savedInstanceState), FRAGMENT_KEY)
					.setTransition(FragmentTransaction.TRANSIT_NONE).commit();
		} else {
			mFragmentTagStack.addAll((Collection<String>) savedInstanceState.getSerializable(SAVE_KEY_STACK));
			restoreFragmentsState();
		}

		getSupportFragmentManager().addOnBackStackChangedListener(this);
	}
	
	@Override
	protected void onResumeFragments() {
		log.d("NECVN>>>" + "onResumeFragments");
		super.onResumeFragments();
//		restoreFragmentsState();
		
	}
	
	@Override
	public void onBackPressed() {
		final FragmentManager fm = getSupportFragmentManager();
		final Fragment f;
		if (mFragmentTagStack.size() > 0) {
			f = fm.findFragmentByTag(mFragmentTagStack.peek());
		} else {
			f = fm.findFragmentByTag(FRAGMENT_KEY);
		}
		
		if (f instanceof OnBackPressListener) {
			if (((OnBackPressListener) f).onBackPress()) {
				return;
			}
		}
		super.onBackPressed();
	}

	@Override
	public void onBackStackChanged() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() == mFragmentTagStack.size()) {
			return;
		}
		
		if (mFragmentTagStack.size() > 0) {
			final FragmentTransaction ft = fm.beginTransaction();
			String tag = mFragmentTagStack.pop();
			if(fm.findFragmentByTag(tag) != null) {
				ft.remove(fm.findFragmentByTag(tag));
			}
			ft.commit();
		}

	}

	public void showFragment(Fragment f, boolean isTransit) {
		String tag = String.format("%s:%d", getClass().getName(),
				mFragmentTagStack.size());
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();

		if (mFragmentTagStack.size() > 0) {
			final Fragment ff = fm.findFragmentByTag(tag);
			ft.hide(ff);
		} else {
			final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
			ft.hide(ff);
		}

		if (fm.findFragmentByTag(tag) == null) {
			ft.add(getFragmentContentId(), f, tag);
		} else {
			ft.replace(getFragmentContentId(), f, tag);
		}

		if (isTransit) {
			ft.addToBackStack(null).setTransition(
					FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		} else {
			ft.addToBackStack(null);
		}
		ft.commit();
		mFragmentTagStack.add(tag);
	}
	
	protected void restoreFragmentsState() {
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
		if (mFragmentTagStack.size() == 0) {
			ft.show(fm.findFragmentByTag(FRAGMENT_KEY));
		} else {
			ft.hide(fm.findFragmentByTag(FRAGMENT_KEY));
			for (int i = 0; i < mFragmentTagStack.size(); i++) {
				String tag = mFragmentTagStack.get(i);
				Fragment f = fm.findFragmentByTag(tag);
				if (i + 1 < mFragmentTagStack.size()) {
					ft.hide(f);
				} else {
					ft.show(f);
				}
			}
		}
		ft.commit();
	}

}

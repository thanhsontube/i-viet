package com.android.iviet.welcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.android.iviet.base.BaseFragmentActivity;

public class SwipeToCloseFragent extends Fragment {
	private GestureDetector mGestureDetector;
	private boolean mIsSwipe = false;
	private static final int MIN_SCROLL_DISTANCE = 30;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mGestureDetector = new GestureDetector(getActivity(), new Gesture());
		View rootView = getView();
		rootView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector.onTouchEvent(event);
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	private class Gesture extends SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (!mIsSwipe && distanceX < -MIN_SCROLL_DISTANCE && getActivity() instanceof BaseFragmentActivity) {
				((BaseFragmentActivity) getActivity()).onBackPressed();
				mIsSwipe = true;
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

}

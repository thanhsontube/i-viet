package com.android.iviet.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {
	public static void goneTranslateAnimation(final View view, float fromXDelta, float toXDelta, float fromYDelta,
			float toYDelta, final OnAnimationCallBack animationCallBack) {
		Animation animate = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		animate.setDuration(500);
		animate.setFillAfter(true);
		animate.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if (animationCallBack != null) {
					animationCallBack.onAnimationStart(animation);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (animationCallBack != null) {
					animationCallBack.onAnimationRepeat(animation);
				}
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				if (animationCallBack != null) {
					animationCallBack.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(animate);

	}

	public static void visibleTranslateAnimation(final View view, float fromXDelta, float toXDelta, float fromYDelta,
			float toYDelta, final OnAnimationCallBack animationCallBack) {
		Animation animate = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		animate.setDuration(500);
		animate.setFillAfter(true);
		animate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
				if (animationCallBack != null) {
					animationCallBack.onAnimationStart(animation);
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if (animationCallBack != null) {
					animationCallBack.onAnimationRepeat(animation);
				}
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (animationCallBack != null) {
					animationCallBack.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(animate);

	}

	public interface OnAnimationCallBack {
		public void onAnimationStart(Animation animation);

		public void onAnimationRepeat(Animation animation);

		public void onAnimationEnd(Animation animation);
	};

}

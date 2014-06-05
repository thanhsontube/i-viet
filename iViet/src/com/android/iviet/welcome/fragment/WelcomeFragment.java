package com.android.iviet.welcome.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.utils.FilterLog;
import com.android.iviet.welcome.adapter.WelcomeFragmentAdapter;
import com.android.iviet.welcome.callbacks.BaseInterface;
import com.android.iviet.welcome.lib.CirclePageIndicator;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

public class WelcomeFragment extends Fragment implements OnPageChangeListener,
		OnClickListener {
	
	FilterLog log = new FilterLog(TAG);
	private static final int TIMEOUT_SWITCH_PAGE = 7000;


	private static final String TAG = "WelcomeFragment";
	
	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();

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
	private View mLoginPanel;
	private ImageButton mBtnEmail;
	private ImageButton mBtnFaceBook;
	private ImageButton mBtnGoolge;
	private ImageButton mBtnRegister;
	private Button mBtnTouchToOpen;
	private static BaseInterface mBaseInterface;
	private List<String> listPermission = new ArrayList<>();

	public static WelcomeFragment createWelcomeFragment(
			BaseInterface baseInterface) {
		mBaseInterface = baseInterface;
		return new WelcomeFragment();
	}

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
		mLoginPanel = rootView.findViewById(R.id.login_panel);
		mBtnEmail = (ImageButton) rootView.findViewById(R.id.login_btn_mail);
		mBtnFaceBook = (ImageButton) rootView
				.findViewById(R.id.login_btn_facebook);
		mBtnGoolge = (ImageButton) rootView.findViewById(R.id.login_btn_google);
		mBtnRegister = (ImageButton) rootView
				.findViewById(R.id.login_btn_register);
		mBtnTouchToOpen = (Button) rootView
				.findViewById(R.id.welcome_btn_touch_to_open);
		mBtnTouchToOpen.setOnClickListener(this);
		mBtnEmail.setOnClickListener(this);
		mBtnFaceBook.setOnClickListener(this);
		mBtnGoolge.setOnClickListener(this);
		mBtnRegister.setOnClickListener(this);
		rootView.findViewById(R.id.welcome_bg).setOnTouchListener(
				new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							hideLoginPanel();
						}
						return false;
					}
				});
		
		//facebook init
		
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		log.d("log>>>" + "session:" + session);
		if (session == null) {
			// if (savedInstanceState != null) {
			// session = Session.restoreSession(getActivity(), null, statusCallback, savedInstanceState);
			// }
			if (session == null) {
				session = new Session(getActivity());
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				listPermission.addAll(session.getPermissions());
				listPermission.add("email");
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback).setPermissions(listPermission));
			}
		}else {
			session.closeAndClearTokenInformation();
		}

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
	public void onPageSelected(final int arg0) {
		try {
			Resources resources = getActivity().getResources();
			int resid = resources.getIdentifier("ic_iviet_background_"
					+ (arg0 + 1), "drawable", getActivity().getPackageName());
			mWelcomeBackground.setBackgroundResource(resid);

		} catch (Exception e) {
		}

	}

	private class AutoSwitchPageTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(TIMEOUT_SWITCH_PAGE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
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

	private void onTouchToOpen() {
		int visible = mLoginPanel.getVisibility();
		if (visible == View.GONE) {
			showLoginPanel();
		} else {
			hideLoginPanel();
		}

//		 startActivity(new Intent(getActivity(), MainActivity.class));
	}

	private void showLoginPanel() {
		int visible = mLoginPanel.getVisibility();
		if (visible != View.GONE) {
			return;
		}
		visibleAnimation(mLoginPanel);
		mBtnTouchToOpen.setText(R.string.welcome_cham_dong);
	}

	private void hideLoginPanel() {
		int visible = mLoginPanel.getVisibility();
		if (visible == View.GONE) {
			return;
		}
		hideAnimation(mLoginPanel);
		mBtnTouchToOpen.setText(R.string.welcome_cham_mo);
	}

	private void fadeInAnimation(final View view) {
		Animation animation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.anim_login);
		animation.setAnimationListener(mLoginAnimationListener);
		view.startAnimation(animation);
	}

	private void hideAnimation(final View view) {
		Animation animate = new TranslateAnimation(0, -view.getWidth(), 0, 0);
		animate.setDuration(500);
		animate.setFillAfter(true);
		animate.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mBtnEmail.setVisibility(View.INVISIBLE);
				mBtnFaceBook.setVisibility(View.INVISIBLE);
				mBtnGoolge.setVisibility(View.INVISIBLE);
				mBtnRegister.setVisibility(View.INVISIBLE);
				view.setVisibility(View.GONE);
			}
		});
		view.startAnimation(animate);

	}

	public void visibleAnimation(final View view) {
		Animation animate = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
		animate.setDuration(500);
		animate.setFillAfter(true);
		animate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				fadeInAnimation(mBtnEmail);
			}
		});
		view.startAnimation(animate);

	}

	private AnimationListener mLoginAnimationListener = new AnimationListener() {
		private int switchId = 0;

		@Override
		public void onAnimationStart(Animation animation) {
			switchId = switchId % 4;
			switch (switchId) {
			case 0:
				mBtnEmail.setVisibility(View.VISIBLE);
				break;
			case 1:
				mBtnFaceBook.setVisibility(View.VISIBLE);
				break;
			case 2:
				mBtnGoolge.setVisibility(View.VISIBLE);
				break;
			case 3:
				mBtnRegister.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			switch (switchId) {
			case 0:
				fadeInAnimation(mBtnFaceBook);
				break;
			case 1:
				fadeInAnimation(mBtnGoolge);
				break;
			case 2:
				fadeInAnimation(mBtnRegister);
				break;
			default:
				break;
			}
			switchId++;
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.welcome_btn_touch_to_open:
			onTouchToOpen();
			break;
		case R.id.login_btn_mail:
			if (mBaseInterface != null) {
				mBaseInterface.onClickInFragment(getClass().getName(),
						R.id.login_btn_mail);
			}
			break;
		case R.id.login_btn_facebook:
			if (mBaseInterface != null) {
//				mBaseInterface.onClickInFragment(getClass().getName(),
//						R.id.login_btn_facebook);
				mBaseInterface.onClickFacebook(this);
			}
			break;
		case R.id.login_btn_google:
			if (mBaseInterface != null) {
				mBaseInterface.onClickInFragment(getClass().getName(),
						R.id.login_btn_google);
			}
			break;
		case R.id.login_btn_register:
			if (mBaseInterface != null) {
				mBaseInterface.onClickInFragment(getClass().getName(),
						R.id.login_btn_register);
			}
			break;
		default:
			break;
		}

	}
	
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			Log.v(TAG, "log>>>" + "call:" + session);
			if (state.isOpened()) {
				mBaseInterface.onFbPrepare(WelcomeFragment.this);
				Log.v(TAG, "log>>>" + "isOpened");
				Request request = Request.newMeRequest(session, new GraphUserCallback() {
					
					@Override
					public void onCompleted(GraphUser user, Response response) {
						Toast.makeText(getActivity(), "email:" + user.asMap().get("email") + ";name:" + user.getName(), Toast.LENGTH_SHORT).show();
						Log.v(TAG, "log>>>" + "Facebook OK : email:" + user.asMap().get("email") + ";name:" + user.getName());
						if(mBaseInterface != null) {
							mBaseInterface.onFbGetInfoSuccess(WelcomeFragment.this);
						}
						
					}
				});
				Request.executeBatchAsync(request);
				
			} else if (state.isClosed()) {
				Log.v(TAG, "log>>>" + "isClosed");
			}
			updateView();
		}

	}

	private void updateView() {
		Log.v(TAG, "log>>>" + "updateView");

	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v(TAG, "log>>>" + "onActivityResult123");
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
	}
	public void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
		} else {
			Session.openActiveSession(getActivity(), this, true, statusCallback);
		}
	}

	public void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}
	
}

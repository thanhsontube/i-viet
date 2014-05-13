package com.android.iviet.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.welcome.fragment.WelcomeFragment;

public class WelcomeActivity extends BaseFragmentActivity {
	private View mLoginPanel;
	private ImageButton mBtnEmail;
	private ImageButton mBtnFaceBook;
	private ImageButton mBtnGoolge;
	private ImageButton mBtnRegister;
	private Button mBtnTouchToOpen;

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		WelcomeFragment welcomeFragment = new WelcomeFragment();
		welcomeFragment.setRetainInstance(true);
		return welcomeFragment;
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.welcome_ll_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_main);
		mLoginPanel = findViewById(R.id.login_panel);
		mBtnEmail = (ImageButton) findViewById(R.id.login_btn_mail);
		mBtnFaceBook = (ImageButton) findViewById(R.id.login_btn_facebook);
		mBtnGoolge = (ImageButton) findViewById(R.id.login_btn_google);
		mBtnRegister = (ImageButton) findViewById(R.id.login_btn_register);
		mBtnTouchToOpen = (Button) findViewById(R.id.welcome_btn_touch_to_open);
		findViewById(R.id.welcome_bg).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					hideLoginPanel();
				}
				return false;
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.welcome_btn_touch_to_open:
			onTouchToOpen();
			break;
		case R.id.login_btn_mail:
			Toast.makeText(WelcomeActivity.this, "Email", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.login_btn_facebook:
			Toast.makeText(WelcomeActivity.this, "Facebook", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.login_btn_google:
			Toast.makeText(WelcomeActivity.this, "Google", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.login_btn_register:
			Toast.makeText(WelcomeActivity.this, "Register", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}

	}

	private void onTouchToOpen() {
		int visible = mLoginPanel.getVisibility();
		if (visible == View.GONE) {
			showLoginPanel();
		} else {
			hideLoginPanel();
		}
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
		Animation animation = AnimationUtils.loadAnimation(
				WelcomeActivity.this, R.anim.anim_login);
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
			// TODO Auto-generated method stub
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

}

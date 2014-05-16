package com.android.iviet.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.welcome.callbacks.BaseInterface;
import com.android.iviet.welcome.fragment.LoginEmailForgetPassFragment;
import com.android.iviet.welcome.fragment.LoginEmailFragment;
import com.android.iviet.welcome.fragment.WelcomeFragment;

public class WelcomeActivity extends BaseFragmentActivity implements
		BaseInterface {

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		WelcomeFragment welcomeFragment = WelcomeFragment
				.createWelcomeFragment(this);
		welcomeFragment.setRetainInstance(true);
		return welcomeFragment;
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.welcome_v_background;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_main);

	}

	@Override
	public void onClickInFragment(String fragmentName, int id) {
		if (fragmentName.equalsIgnoreCase(LoginEmailFragment.class.getName())) {
			switch (id) {
			case R.id.login_email_btn_login:
				// TODO
				// API login
				break;
			case R.id.login_tv_forget_pass:
			
//				showFragment(
//						LoginEmailForgetPassFragment
//								.createLoginEmailForgetPassFragment(WelcomeActivity.this),
//						true);
			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(LoginEmailForgetPassFragment.class
				.getName())) {
			switch (id) {
			case R.id.login_email_btn_get_pass:
				// TODO
				// API get Pass
				break;

			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(WelcomeFragment.class
				.getName())) {
			switch (id) {
			case R.id.login_btn_mail:
				showFragment(
						LoginEmailFragment
								.createLoginEmailFragment(WelcomeActivity.this),
						true);
				break;

			default:
				break;
			}
		}
	}
	
	

}

package com.android.iviet.welcome;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.main.MainActivity;
import com.android.iviet.welcome.callbacks.BaseInterface;
import com.android.iviet.welcome.fragment.LoginEmailForgetPassFragment;
import com.android.iviet.welcome.fragment.LoginEmailFragment;
import com.android.iviet.welcome.fragment.PolicyFragment;
import com.android.iviet.welcome.fragment.RegisterFragment;
import com.android.iviet.welcome.fragment.WelcomeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class WelcomeActivity extends BaseFragmentActivity implements BaseInterface, ConnectionCallbacks,
		OnConnectionFailedListener {

	private static final int RC_SIGN_IN = 0;

	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	private ConnectionResult mConnectionResult;

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		WelcomeFragment welcomeFragment = WelcomeFragment.createWelcomeFragment(this);
		welcomeFragment.setRetainInstance(true);
		return welcomeFragment;
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.welcome_fm_layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_main);
		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API, null).addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}

	@Override
	public void onClickInFragment(String fragmentName, int id) {
		if (fragmentName.equalsIgnoreCase(LoginEmailFragment.class.getName())) {
			switch (id) {
			case R.id.login_email_btn_login:
				// TODO
				// API login
				startActivity(new Intent(this, MainActivity.class));
				break;
			case R.id.login_tv_forget_pass:

				showFragment(LoginEmailForgetPassFragment.createLoginEmailForgetPassFragment(WelcomeActivity.this),
						true);
			case R.id.login_btn_google:
				signInWithGplus();
				break;
			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(LoginEmailForgetPassFragment.class.getName())) {
			switch (id) {
			case R.id.login_email_btn_get_pass:
				// TODO
				// API get Pass
				break;

			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(WelcomeFragment.class.getName())) {
			switch (id) {
			case R.id.login_btn_mail:
				showFragment(LoginEmailFragment.createLoginEmailFragment(WelcomeActivity.this), true);
				break;
			case R.id.login_btn_register:
				showFragment(RegisterFragment.createRegisterFragment(WelcomeActivity.this), true);
				break;
			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(RegisterFragment.class.getName())) {
			switch (id) {
			case R.id.register_btn_register:
				// TODO
				// API dang ky
				break;
			case R.id.register_tv_agree:
				showFragment(PolicyFragment.createPolicyFragment(WelcomeActivity.this), true);
				break;

			default:
				break;
			}
		} else if (fragmentName.equalsIgnoreCase(PolicyFragment.class.getName())) {
			switch (id) {
			case R.id.register_policy_btn_back:
				onBackPressed();
				break;
			default:
				break;
			}
		}
	}
	 /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            resolveSignInError();
        }
    }
    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }
    }
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// Get user's information
		getProfileInformation();

	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
			}
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.e("TAG", "Name: " + personName + ", plusProfile: " + personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);

				// txtName.setText(personName);
				// txtEmail.setText(email);

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() - 2) + PROFILE_PIC_SIZE;
				//
				// new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

			} else {
				Toast.makeText(getApplicationContext(), "Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.android.iviet.welcome;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.dialog.ReportDialog;
import com.android.iviet.dialog.WaitingDialog;
import com.android.iviet.main.MainActivity;
import com.android.iviet.utils.CommonUtils;
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
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	private ConnectionResult mConnectionResult;

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		WelcomeFragment welcomeFragment = WelcomeFragment.createWelcomeFragment(this);
		welcomeFragment.setRetainInstance(true);
		return welcomeFragment;
		
//		MyFragment welcomeFragment = new MyFragment();
//		return welcomeFragment;
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
				login();
				break;
			case R.id.login_tv_forget_pass:

				showFragment(LoginEmailForgetPassFragment.createLoginEmailForgetPassFragment(WelcomeActivity.this),
						true);
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
			case R.id.login_btn_google:
				signInWithGplus();
				break;
			case R.id.login_btn_register:
				showFragment(RegisterFragment.createRegisterFragment(WelcomeActivity.this), true);
				break;
				
			case R.id.login_btn_facebook:
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

	private void login() {
		startActivity(new Intent(this, MainActivity.class));
	}

	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!CommonUtils.isWifiOn(WelcomeActivity.this) && !CommonUtils.is3GOn(WelcomeActivity.this)) {
			CommonUtils.showInfoDialog(WelcomeActivity.this, WelcomeActivity.this.getString(R.string.loi),
					WelcomeActivity.this.getString(R.string.kiem_tra_ket_noi_internet));
			return;
		}
		if (!mGoogleApiClient.isConnecting()) {
			mGoogleApiClient.connect();
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		mConnectionResult = result;
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}
		 resolveSignInError(result);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v("MAIN", "log>>>" + "MAIN onActivityResult");
	    super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			Log.v("", "log>>>" + "RC_SIGN_IN");
			// if (!mGoogleApiClient.isConnecting()) {
			// mGoogleApiClient.connect();
			// }
			if (resultCode == RESULT_OK) {
				Toast.makeText(WelcomeActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
			} else {
				// Toast.makeText(WelcomeActivity.this, ", Toast.LENGTH_LONG).show();
				GooglePlayServicesUtil.getErrorDialog(mConnectionResult.getErrorCode(), this, 0).show();
			}

		}
	    
	}
//	@Override
//	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//		super.onActivityResult(requestCode, requestCode, intent);
//		if (requestCode == RC_SIGN_IN) {
////			if (!mGoogleApiClient.isConnecting()) {
////				mGoogleApiClient.connect();
////			}
//			if (responseCode == RESULT_OK) {
//				Toast.makeText(WelcomeActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
//			} else {
////				Toast.makeText(WelcomeActivity.this, ", Toast.LENGTH_LONG).show();
//				GooglePlayServicesUtil.getErrorDialog(mConnectionResult.getErrorCode(), this, 0).show();
//			}
//
//		}
//	}

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
				// String personPhotoUrl = currentPerson.getImage().getUrl();
				// String personGooglePlusProfile = currentPerson.getUrl();
				// String email =
				// Plus.AccountApi.getAccountName(mGoogleApiClient);
				Toast.makeText(WelcomeActivity.this, "Hello " + personName, Toast.LENGTH_SHORT).show();

				// Log.e("TAG", "Name: " + personName + ", plusProfile: " +
				// personGooglePlusProfile + ", email: " + email
				// + ", Image: " + personPhotoUrl);

				// txtName.setText(personName);
				// txtEmail.setText(email);

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				// personPhotoUrl = personPhotoUrl.substring(0,
				// personPhotoUrl.length() - 2) + PROFILE_PIC_SIZE;
				//
				// new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
				login();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
    public void onClickFacebook(Fragment f) {
	    ((WelcomeFragment)f).onClickLogin();
    }

	@Override
    public void onFbGetInfoSuccess(Fragment f) {
		Log.v("", "log>>>" + "onFbGetInfoSuccess");
//	    Toast.makeText(this, "Login by facebook is successful", Toast.LENGTH_SHORT).show();
	    login();
    }

	@Override
    public void onFbGetInfoFail(Fragment f) {
	    Toast.makeText(this, "Can not log in by facebook", Toast.LENGTH_SHORT).show();
    }

	@Override
    public void onFbPrepare(Fragment f) {
		Log.v("", "log>>>" + "onFbPrepare");
//		WaitingDialog fd = new WaitingDialog();
		
//		getSupportFragmentManager().beginTransaction().add(fd, fd.getClass().getName())
//		        .commitAllowingStateLoss();
		
//		showFragment(fd, true);
	    
    }

}

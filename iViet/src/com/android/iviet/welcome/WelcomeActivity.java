package com.android.iviet.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.main.MainActivity;
import com.android.iviet.welcome.fragment.SlideFragment;

public class WelcomeActivity extends BaseFragmentActivity implements OnClickListener{

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		SlideFragment slideFragment = new SlideFragment();
		slideFragment.setRetainInstance(true);
		return slideFragment;
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
		Button btnChamMo = (Button) findViewById(R.id.welcome_btn_touch_to_open);
		btnChamMo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.welcome_btn_touch_to_open:
			final Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
	

}

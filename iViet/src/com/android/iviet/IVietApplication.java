package com.android.iviet;

import java.io.IOException;

import android.app.Application;

import com.android.iviet.connection.BasicAccessPathGenerator;
import com.android.iviet.connection.ContentManager;

public class IVietApplication extends Application {
	
	private ContentManager mContentManager;
	private BasicAccessPathGenerator mBasicAccessPathGenerator;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mContentManager = new ContentManager(this, 10);
			mBasicAccessPathGenerator = new BasicAccessPathGenerator(getApplicationContext());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public ContentManager getContentManager() {
		return mContentManager;
	}
	
	public BasicAccessPathGenerator getBasicAccessPathGenerator() {
		return mBasicAccessPathGenerator;
	}
}

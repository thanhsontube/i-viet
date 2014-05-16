package com.android.iviet;

import java.io.IOException;

import android.app.Application;

import com.android.iviet.connection.BasicAccessPathGenerator;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.main.drawer.MainDrawerItemGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IVietApplication extends Application {
	
	private ContentManager mContentManager;
	private BasicAccessPathGenerator mBasicAccessPathGenerator;
	private MainDrawerItemGenerator mMainDrawerItemGenerator;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mContentManager = new ContentManager(this, 10);
			mBasicAccessPathGenerator = new BasicAccessPathGenerator(getApplicationContext());
			mMainDrawerItemGenerator = new MainDrawerItemGenerator(getApplicationContext());
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .build();
			ImageLoader.getInstance().init(config);
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
	
	public MainDrawerItemGenerator getDrawerItemGenerator() {
		return mMainDrawerItemGenerator;
	}
}

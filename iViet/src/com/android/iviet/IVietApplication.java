package com.android.iviet;

import java.io.IOException;

import android.app.Application;

import com.android.iviet.about.AboutIParent;
import com.android.iviet.about.AboutParent;
import com.android.iviet.base.Factory;
import com.android.iviet.connection.BasicAccessPathGenerator;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.main.drawer.MainDrawerItemGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IVietApplication extends Application implements Factory{
	
	private ContentManager mContentManager;
	private BasicAccessPathGenerator mBasicAccessPathGenerator;
	private MainDrawerItemGenerator mMainDrawerItemGenerator;
	private AboutIParent mAboutIParent;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mContentManager = new ContentManager(this, 10);
			mBasicAccessPathGenerator = new BasicAccessPathGenerator(getApplicationContext());
			mMainDrawerItemGenerator = new MainDrawerItemGenerator(getApplicationContext());
			mAboutIParent = new AboutParent(getApplicationContext());
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .build();
			ImageLoader.getInstance().init(config);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	public ContentManager getContentManager() {
		return mContentManager;
	}
	@Override
	public BasicAccessPathGenerator getBasicAccessPathGenerator() {
		return mBasicAccessPathGenerator;
	}
	@Override
	public MainDrawerItemGenerator getDrawerItemGenerator() {
		return mMainDrawerItemGenerator;
	}
	@Override
    public AboutIParent getAboutParent() {
	    return mAboutIParent;
    }
	
	
}

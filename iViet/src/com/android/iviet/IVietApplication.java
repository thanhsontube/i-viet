package com.android.iviet;

import java.io.IOException;

import android.app.Application;

import com.android.iviet.about.AboutIParent;
import com.android.iviet.about.AboutParent;
import com.android.iviet.base.Factory;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.PathIAccess;
import com.android.iviet.main.drawer.MainDrawerItemGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IVietApplication extends Application implements Factory{
	
	private ContentManager mContentManager;
	private PathIAccess mPathIAccess;
	private MainDrawerItemGenerator mMainDrawerItemGenerator;
	private AboutIParent mAboutIParent;
	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mContentManager = new ContentManager(this, 10);
			mPathIAccess = new PathAccess(getApplicationContext());
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
	public PathIAccess getPathAccess() {
		return mPathIAccess;
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

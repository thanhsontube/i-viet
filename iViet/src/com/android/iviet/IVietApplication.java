package com.android.iviet;

import java.io.IOException;

import android.app.Application;
import android.content.Context;

import com.android.iviet.about.AboutIParent;
import com.android.iviet.about.AboutParent;
import com.android.iviet.base.Factory;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.PathIAccess;
import com.android.iviet.main.drawer.MainDrawerItemGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
			initImageLoader(getApplicationContext());
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
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	
}

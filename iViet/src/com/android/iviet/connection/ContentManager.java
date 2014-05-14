package com.android.iviet.connection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.support.v4.util.LruCache;

import com.android.iviet.utils.FilterLog;

public class ContentManager {
	private static final FilterLog log = new FilterLog(ContentManager.class.getSimpleName());
	
	private  Context context;

	private File mCacheDir;
	private LruCache<String, File> mCache;
	
	public ContentManager(Context context, int cacheMB) throws IOException  {
		this.context = context.getApplicationContext();
		this.mCacheDir = new File(context.getCacheDir(), ".cache");
		this.mCache = new LruCache<String, File>(cacheMB * 1024) {
			@Override
			protected int sizeOf(String key, File value) {
				return (int) (value.length() / 1024);
			}
			@Override
			protected void entryRemoved(boolean evicted, String key, File oldValue, File newValue) {
				synchronized (this) {
					log.v("entryRemoved evicted:" + evicted + " " + oldValue.getAbsolutePath());
					oldValue.delete();
				}
			}
		};
	}
	//TODO putCache
	void putCache(String key, InputStream in) throws IOException {
		
	}
	
	public void load(final ContentLoader<?> loader) {
		loader.execute(this);
	}
	
	public void cancel(ContentLoader<?> loader) {
		loader.cancel();
	}
}

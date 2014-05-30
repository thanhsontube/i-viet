package com.android.iviet.connection;

import java.net.URI;
import java.net.URISyntaxException;

import com.android.iviet.R;
import com.android.iviet.utils.FilterLog;

import android.content.Context;

public class PathAccess implements PathIAccess {
	private static final String TAG = "PathAccess";
	FilterLog log = new FilterLog(TAG);
	String mBaseUrl;
	Context context;

	public PathAccess(Context context) {
		this.context = context.getApplicationContext();
		mBaseUrl = context.getString(R.string.base_url);

	}

	@Override
	public URI everything() {
		log.d("log>>>" + "everything");
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public URI newest() {
		log.d("log>>>" + "newest");
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public URI feature() {
		log.d("log>>>" + "feature");
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
    public URI search() {
		log.d("log>>>" + "seach");
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
    }

	@Override
    public URI notificaiton() {
		log.d("log>>>" + "notificaiton");
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
    }

}

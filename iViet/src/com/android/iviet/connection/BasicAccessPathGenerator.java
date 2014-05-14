package com.android.iviet.connection;

import java.net.URI;
import java.net.URISyntaxException;

import com.android.iviet.R;

import android.content.Context;

public class BasicAccessPathGenerator implements AccessPathGenerator {
	String mBaseUrl;
	Context context;

	public BasicAccessPathGenerator(Context context) {
		this.context = context.getApplicationContext();
		mBaseUrl = context.getString(R.string.base_url);

	}

	@Override
	public URI everything() {
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public URI newest() {
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public URI feature() {
		String path = "m/questions/all?cursor=&userToken=u50";
		try {
			return new URI(mBaseUrl + path);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

}

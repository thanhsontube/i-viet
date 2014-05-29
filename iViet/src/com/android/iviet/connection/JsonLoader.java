package com.android.iviet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;

abstract public class JsonLoader<T> extends ContentLoader<T> {

	public JsonLoader(HttpUriRequest request, boolean useCache) {
		super(request, useCache);
	}

	@Override
	protected T handleStream(InputStream in) throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int buf = -1;
			while ((buf = br.read()) >= 0) {
				sb.append((char) buf);
			}
			log.d(sb.toString());
			return handleJson(new JSONArray(sb.toString()));
		} catch (JSONException e) {
			log.e(e.getMessage(), e);
			throw new IOException(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

	}

	protected abstract T handleJson(JSONArray json) throws IOException, JSONException;

}

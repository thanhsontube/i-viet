package com.android.iviet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.HttpUriRequest;

import com.android.iviet.json.DataObject;
import com.android.iviet.main.dto.MainDto;
import com.google.gson.Gson;

public abstract class MainLoader extends ContentLoader<MainDto> {

	public MainLoader(HttpUriRequest request, boolean useCache) {
		super(request, useCache);
	}
	
	@Override
	public MainDto handleStream(InputStream in) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int buf = -1;
			while ((buf = br.read()) >= 0) {
				sb.append((char) buf);
			}
			log.d("necvn>>>" + sb.toString());
			
			return handleJson(sb.toString());
//			return handleJson(new JSONObject(sb.toString()));
		} catch (Exception e) {
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
	
	public MainDto handleJson (String json) {
		try {
	        Gson gson = new Gson();
	        return gson.fromJson(json, MainDto.class);
        } catch (Exception e) {
	        // TODO: handle exception
        	return null;
        }
	}

//	protected abstract MainDto handleJson(JSONObject json) throws IOException, JSONException;

}

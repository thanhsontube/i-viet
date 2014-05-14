package com.android.iviet.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.iviet.json.BaseObject;
import com.android.iviet.json.DataObject;
import com.android.iviet.main.dto.DataRootDto;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.utils.FilterLog;

public abstract class MainLoader extends ContentLoader<DataRootDto> {
	protected static final String TAG = "MainLoader";
	FilterLog log = new FilterLog(TAG);

	public MainLoader(HttpUriRequest request, boolean useCache) {
		super(request, useCache);
	}
	
	@Override
	public DataRootDto handleStream(InputStream in) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			int buf = -1;
			while ((buf = br.read()) >= 0) {
				sb.append((char) buf);
			}
//			log.d("necvn>>>" + sb.toString());
			
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
	
	public DataRootDto handleJson (String json) {
		try {
			//link json:
			//http://www.iviet.com/m/questions/all?cursor=&userToken=u50
//			log.d("NECVN>>>" + "handleJson");
//	        Gson gson = new Gson();
//	        return gson.fromJson(json, MainDto.class);
			DataRootDto dto = new DataRootDto();
			JSONArray ja = new JSONArray(json);
			log.d("NECVN>>>" + "ja:" + ja.length());
			
			List<BaseObject> list = new ArrayList<BaseObject>();
			for (int i = 0; i < ja.length(); i ++) {
				JSONObject jo2 = ja.getJSONObject(i);
				if (!jo2.isNull("cursor")) {
					String cursor = jo2.getString("cursor");
					log.d("NECVN>>>" + "cursor:" + cursor) ;
					
				} else {
					log.d("NECVN>>>" + "NULL cursor:"+ i);
				}
				
				if(!jo2.isNull("newest")) {
					JSONArray ja2 = jo2.getJSONArray("newest");
					log.d("NECVN>>>" + "ja2:" + ja2.length());
					for (int j = 0; j < ja2.length(); j++) {
						JSONObject jo3 = ja2.getJSONObject(j);
						String userAvatar = jo3.getString("user_avatar");
						String topicName = jo3.getString("topic_name");
						String snapshotContent = jo3.getString("snapshot_content");
						String snapshotImg = jo3.getString("snapshot_img");
						BaseObject baseObject = new BaseObject();
						baseObject.setUser_avatar(userAvatar);
						baseObject.setTopic_name(topicName);
						baseObject.setSnapshot_content(snapshotContent);
						baseObject.setSnapshot_img(snapshotImg);
						list.add(baseObject);
                    }
					
				}
				MainDto dataObject = new MainDto();
				dataObject.setListdata(list);
				List<MainDto> listdDataObjects = new ArrayList<MainDto>();
				listdDataObjects.add(dataObject);
				dto.setList(listdDataObjects);
				return dto;
				
				
			}
			
			return null;
        } catch (Exception e) {
        	Log.e(TAG, "NECVN>>>" + "handleJson:" + e.toString());
        	return null;
        }
	}
	

//	protected abstract MainDto handleJson(JSONObject json) throws IOException, JSONException;

}

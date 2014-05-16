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
//			log.d("log>>>" + sb.toString());
			
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
//			log.d("log>>>" + "handleJson");
//	        Gson gson = new Gson();
//	        return gson.fromJson(json, MainDto.class);
			DataRootDto dto = new DataRootDto();
			JSONArray ja = new JSONArray(json);
			log.d("log>>>" + "ja:" + ja.length());
			
			List<BaseObject> list = new ArrayList<BaseObject>();
			List<MainDto> listdMainDto = new ArrayList<MainDto>();
			for (int i = 0; i < ja.length(); i ++) {
				JSONObject jo2 = ja.getJSONObject(i);
				if (!jo2.isNull("cursor")) {
					String cursor = jo2.getString("cursor");
					log.d("log>>>" + "cursor:" + cursor) ;
					
				} else {
					log.d("log>>>" + "NULL cursor:"+ i);
				}
				
				if(!jo2.isNull("newest")) {
					JSONArray ja2 = jo2.getJSONArray("newest");
					log.d("log>>>" + "ja2:" + ja2.length());
					for (int j = 0; j < ja2.length(); j++) {
						JSONObject jo3 = ja2.getJSONObject(j);
						
						String user_avatar = jo3.getString("user_avatar");
						
						String user_name = jo3.getString("user_name");
						String created_on = jo3.getString("created_on");
						
						String answer_user_name = jo3.getString("answer_user_name");
						String recent_answer_date = jo3.getString("recent_answer_date");
						
						String snapshot_img = jo3.getString("snapshot_img");
						
						String title = jo3.getString("title");
						String snapshot_content = jo3.getString("snapshot_content");
						
						int vote_ups = jo3.getInt("vote_ups");
						int number_answers = jo3.getInt("number_answers");
						int number_views = jo3.getInt("number_views");
						
						
						
						String user_id = jo3.getString("user_id");
						String topic_icon = jo3.getString("topic_icon");
						String topic_name = jo3.getString("topic_name");
						String theme_color = jo3.getString("theme_color");
						String is_anonymous = jo3.getString("is_anonymous");
						String question_id = jo3.getString("question_id");
						
						
						BaseObject baseObject = new BaseObject(user_avatar, user_name, created_on, answer_user_name, recent_answer_date, snapshot_img, title, snapshot_content, vote_ups, number_answers, number_views, user_id, topic_icon, topic_name, theme_color, is_anonymous, question_id);
						list.add(baseObject);
						MainDto dataObject = new MainDto();
						dataObject.setListdata(list);
						listdMainDto.add(dataObject);
                    }
					dto.setList(listdMainDto);
				}
			}
			return dto;
        } catch (Exception e) {
        	Log.e(TAG, "log>>>" + "handleJson:" + e.toString());
        	return null;
        }
	}
	

//	protected abstract MainDto handleJson(JSONObject json) throws IOException, JSONException;

}

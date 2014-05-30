package com.android.iviet.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.iviet.newfeed.NotiDto;
import com.android.iviet.utils.FilterLog;

abstract public class NotificaitonLoader extends JsonLoader<List<NotiDto>> {
	public static final String TAG = "NotificaitonLoader";
	FilterLog log = new FilterLog(TAG);

	public NotificaitonLoader(HttpUriRequest request, boolean useCache) {
	    super(request, useCache);
    }
	
	@Override
	protected List<NotiDto> handleJson(JSONArray json) throws IOException, JSONException {
		log.d("log>>>" + "handleJson");

		try {
			//link json:
			//http://www.iviet.com/m/questions/all?cursor=&userToken=u50
//			log.d("log>>>" + "handleJson");
//	        Gson gson = new Gson();
//	        return gson.fromJson(json, MainDto.class);
			JSONArray ja = json;
			log.d("log>>>" + "ja:" + ja.length());
			
			List<NotiDto> list = new ArrayList<NotiDto>();
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
						
						NotiDto notiDto = new NotiDto();
						notiDto.setTitle(title); 
						notiDto.setSnapshot_content(snapshot_content);
						notiDto.setUser_avatar(user_avatar);
						notiDto.setRecent_answer_date(recent_answer_date);
						list.add(notiDto);
						
                    }
				}
			}
			return list;
        } catch (Exception e) {
        	Log.e(TAG, "log>>>" + "handleJson:" + e.toString());
        	return null;
        }
	
	}
	
}

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
	protected List<NotiDto> handleJson(JSONArray jsonRoot) throws IOException, JSONException {
		log.d("log>>>" + "handleJson");

		List<NotiDto> mList = new ArrayList<>();
		try {
			for (int i = 0; i < jsonRoot.length(); i ++) {
				JSONObject jsonItem = jsonRoot.getJSONObject(i);
				//NEWEST
//				if (!jsonItem.isNull("newest")) {
//					
//					JSONArray jsonArray = jsonItem.getJSONArray("newest");
//					log.d("log>>>" + "jsonArray newest:" + jsonArray.length());
//					mList = parseJsonArray(jsonArray);
//					break;
//				}
				
				if (!jsonItem.isNull("featured")) {
					JSONArray jsonArray = jsonItem.getJSONArray("featured");
					log.d("log>>>" + "jsonArray featured:" + jsonArray.length());
					mList = parseJsonArray(jsonArray);
				}
//				
//				if (!jsonItem.isNull("yours")) {
//					JSONArray jsonArray = jsonItem.getJSONArray("yours");
//					log.d("log>>>" + "jsonArray yours:" + jsonArray.length());
//				}
			}
			return mList;
        } catch (Exception e) {
        	Log.e(TAG, "log>>>" + "handleJson:" + e.toString());
        	return null;
        }
	
	}
	
	private List<NotiDto> parseJsonArray(JSONArray jsonArray) throws JSONException {
		List<NotiDto> listNotiDto = new ArrayList<>();
		for (int j = 0; j < jsonArray.length(); j++) {
	    	JSONObject jsonChild = jsonArray.getJSONObject(j);
	    	
	    	String user_avatar = jsonChild.getString("user_avatar");
	    	
	    	String user_name = jsonChild.getString("user_name");
	    	String created_on = jsonChild.getString("created_on");
	    	
	    	String answer_user_name = jsonChild.getString("answer_user_name");
	    	String recent_answer_date = jsonChild.getString("recent_answer_date");
	    	
	    	String snapshot_img = jsonChild.getString("snapshot_img");
	    	
	    	String title = jsonChild.getString("title");
	    	String snapshot_content = jsonChild.getString("snapshot_content");
	    	
	    	int vote_ups = jsonChild.getInt("vote_ups");
	    	int number_answers = jsonChild.getInt("number_answers");
	    	int number_views = jsonChild.getInt("number_views");
	    	
	    	
	    	
	    	String user_id = jsonChild.getString("user_id");
	    	String topic_icon = jsonChild.getString("topic_icon");
	    	String topic_name = jsonChild.getString("topic_name");
	    	String theme_color = jsonChild.getString("theme_color");
	    	String is_anonymous = jsonChild.getString("is_anonymous");
	    	String question_id = jsonChild.getString("question_id");
	    	
	    	
	    	NotiDto NotiDto = new NotiDto(user_avatar, user_name, created_on, answer_user_name, recent_answer_date, snapshot_img, title, snapshot_content, vote_ups, number_answers, number_views, user_id, topic_icon, topic_name, theme_color, is_anonymous, question_id);
	    	listNotiDto.add(NotiDto);
	    }
		return listNotiDto;
    }
	
}

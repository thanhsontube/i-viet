package com.android.iviet.newfeed;

import com.android.iviet.connection.BaseObject;

public class NotiDto extends BaseObject {

	public NotiDto() {
	    super();
	    // TODO Auto-generated constructor stub
    }

	public NotiDto(String user_avatar, String user_name, String created_on, String answer_user_name,
            String recent_answer_date, String snapshot_img, String title, String snapshot_content, int vote_ups,
            int number_answers, int number_views, String user_id, String topic_icon, String topic_name,
            String theme_color, String is_anonymous, String question_id) {
	    super(user_avatar, user_name, created_on, answer_user_name, recent_answer_date, snapshot_img, title, snapshot_content,
	            vote_ups, number_answers, number_views, user_id, topic_icon, topic_name, theme_color, is_anonymous, question_id);
	    // TODO Auto-generated constructor stub
    }
	
}

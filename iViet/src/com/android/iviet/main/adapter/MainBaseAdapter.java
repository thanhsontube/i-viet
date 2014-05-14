package com.android.iviet.main.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.main.dto.MainDto;

public class MainBaseAdapter extends ArrayAdapter<MainDto>{
	List<MainDto> list;
	Context context;
	LayoutInflater inflater;
	public MainBaseAdapter (Context context, List<MainDto> list) {
		super(context, 0, list);
		this.context = context;
		this.list = list; 
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setData(List<MainDto> objects) {
		list.clear();
		list.addAll(objects);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		if (v == null) {
			v = inflater.inflate(R.layout.row_main, parent, false);
			holder = new Holder();
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		return v;
	}
	
	static class Holder {
		public ImageView userAvatar;
		public TextView userName;
		public TextView createdName;
		public TextView answerUserName;
		public TextView recentAnswerDate;
		public ImageView snapshotImg;
		public TextView title;
		public TextView snapshotContent;
		public TextView voteUps;
		public TextView numberAnswers;
		public TextView numberViews;
		
	}
	
}

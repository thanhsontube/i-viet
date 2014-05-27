package com.android.iviet.user;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.iviet.R;

public class ProfileAdapter extends ArrayAdapter<ProfileDto> {
	List<ProfileDto> list;
	Context context;
	public ProfileAdapter(Context context, List<ProfileDto> list) {
		super(context, 0, list);
		this.list = list;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   View v = convertView;
	   Holder holder;
	   if (v == null) {
		   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   v = inflater.inflate(R.layout.profile_row, parent, false);
		   holder = new Holder();
		   holder.img = (ImageView) v.findViewWithTag("image");
		   v.setTag(holder);
	   } else {
		   holder = (Holder) v.getTag();
	   }
	   
	   ProfileDto dto = list.get(position);
	   return v;
	}
	
	static class Holder {
		public ImageView img;
	}
}

package com.android.iviet.main.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.connection.BaseObject;
import com.android.iviet.main.fragment.Top1Fragment;
import com.android.iviet.utils.DatetimeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class MainBaseAdapter extends ArrayAdapter<BaseObject>{
	List<BaseObject> list;
	Context context;
	LayoutInflater inflater;
	Top1Fragment f;
	ImageLoader imageLoader;
	private DisplayImageOptions optionsAvatar;
	private DisplayImageOptions optionsContent;
	public MainBaseAdapter (Context context, List<BaseObject> list, Top1Fragment f) {
		super(context, 0, list);
		this.context = context;
		this.list = list; 
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.f = f;
		
		imageLoader = ImageLoader.getInstance();
		optionsAvatar = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(100))
		.build();
		
		optionsContent = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.iviet_temp)
		.showImageForEmptyUri(R.drawable.iviet_temp)
		.showImageOnFail(R.drawable.iviet_temp)
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0))
		.build();
	}
	
	public void setData(List<BaseObject> objects) {
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
			holder.userAvatar = (ImageView) v.findViewById(R.id.user_avatar);
			holder.userName = (TextView) v.findViewById(R.id.user_name);
			holder.createdName = (TextView) v.findViewById(R.id.created_on);
			holder.answerUserName = (TextView) v.findViewById(R.id.answer_user_name);
			holder.recentAnswerDate = (TextView) v.findViewById(R.id.recent_answer_date);
			holder.snapshotImg = (ImageView) v.findViewById(R.id.snapshot_img);
			holder.title = (TextView) v.findViewById(R.id.title);
			holder.snapshotContent = (TextView) v.findViewById(R.id.snapshot_content);
			holder.voteUps = (TextView) v.findViewById(R.id.vote_ups);
			holder.numberAnswers = (TextView) v.findViewById(R.id.number_answers);
			holder.numberViews = (TextView) v.findViewById(R.id.number_views);
			holder.themColor = (LinearLayout) v.findViewById(R.id.them_color2);
			
			holder.snapshotImg.setTag(position);
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		
		final BaseObject base = list.get(position);
		
		
		holder.userName.setText(base.user_name);
		holder.createdName.setText(getDayAgo(base.created_on, true));
		
		holder.answerUserName.setText(base.answer_user_name);
		holder.recentAnswerDate.setText(getDayAgo(base.recent_answer_date, false));
		if(base.getNumber_answers() < 1) {
			holder.answerUserName.setVisibility(View.GONE);
			holder.recentAnswerDate.setVisibility(View.GONE);
		}
		holder.title.setText(base.title);
		holder.snapshotContent.setText(base.snapshot_content);
		holder.voteUps.setText(String.valueOf(base.vote_ups));
		holder.numberAnswers.setText(String.valueOf(base.number_answers));
		holder.numberViews.setText(String.valueOf(base.number_views));
		holder.themColor.setBackgroundColor(Color.parseColor("#"+base.theme_color));
		//avatar
		
		imageLoader.displayImage(base.getSnapshot_img(), holder.snapshotImg, optionsContent, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				holder.snapshotImg.setScaleType(ScaleType.CENTER_CROP);
				
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
		});
		imageLoader.displayImage(base.getUser_avatar(), holder.userAvatar, optionsAvatar, null);
		
		holder.userAvatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Avatar click", Toast.LENGTH_SHORT).show();
				if(f.mListener != null) {
					f.mListener.onTop1AvatarClicked(f, base);
				}
				
			}
		});
		
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
		public LinearLayout themColor;
		
	}
	
	public String getDayAgo(String str, boolean isAsk) {
		try {
			long dateLong = DatetimeUtils.stringToDate(str);
			if (isAsk) {
				return DatetimeUtils.getTimeAgoVnAsk(dateLong, context);

			}
			return DatetimeUtils.getTimeAgoVnAnswer(dateLong, context);

		} catch (Exception e) {
			return null;
		}
	}
	
}

package com.android.iviet.friend;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.utils.DatetimeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FriendAdapter extends ArrayAdapter<FriendDto> {
	private List<FriendDto> mList;
	private Context mContext;
	ImageLoader imageLoader;
	private DisplayImageOptions options;

	public FriendAdapter(Context context, List<FriendDto> list) {
		super(context, 0, list);
		mContext = context;
		mList = list;

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(100))
		        .build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_friend, parent, false);
			holder = new Holder();
			holder.imgAvatar = (ImageView) v.findViewWithTag("icon");
			holder.imgStatus = (ImageView) v.findViewWithTag("chat_status");
			holder.txtTitle = (TextView) v.findViewWithTag("title");
			holder.txtChat = (TextView) v.findViewWithTag("chat_message");
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		final FriendDto dto = mList.get(position);
		holder.txtTitle.setText(dto.getUser_name());
		holder.txtChat.setText(getDayAgo(dto.getSnapshot_content(), false, getContext()));
		imageLoader.displayImage(dto.getUser_avatar(), holder.imgAvatar, options, null);
		if ((position & 1) == 0) {
			v.setBackgroundResource(R.drawable.btn_common_selector1);
		} else {
			v.setBackgroundResource(R.drawable.btn_common_selector2);
		}
		return v;
	}

	public void setData(List<FriendDto> objects) {
		mList.clear();
		mList.addAll(objects);
		notifyDataSetChanged();
	}

	static class Holder {
		ImageView imgAvatar;
		ImageView imgStatus;
		TextView txtTitle;
		TextView txtChat;
	}

	public String getDayAgo(String str, boolean isAsk, Context context) {
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

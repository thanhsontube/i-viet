package com.android.iviet.newfeed;

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

public class NotiAdapter extends ArrayAdapter<NotiDto> {
	private List<NotiDto> mList;
	private Context mContext;
	ImageLoader imageLoader;
	private DisplayImageOptions options;

	public NotiAdapter(Context context, List<NotiDto> list) {
		super(context, 0, list);
		mContext = context;
		mList = list;

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
		        .build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_noti, parent, false);
			holder = new Holder();
			holder.img = (ImageView) v.findViewWithTag("icon");
			holder.txtTitle = (TextView) v.findViewWithTag("title");
			holder.txtTime = (TextView) v.findViewWithTag("time");
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		final NotiDto dto = mList.get(position);
		holder.txtTitle.setText(dto.getTitle());
		holder.txtTime.setText(getDayAgo(dto.recent_answer_date, false, getContext()));
		imageLoader.displayImage(dto.getUser_avatar(), holder.img, options, null);
		if ((position & 1) == 0) {
			v.setBackgroundResource(R.drawable.btn_common_selector1);
		} else {
			v.setBackgroundResource(R.drawable.btn_common_selector2);
		}
		return v;
	}

	public void setData(List<NotiDto> objects) {
		mList.clear();
		mList.addAll(objects);
		notifyDataSetChanged();
	}

	static class Holder {
		ImageView img;
		TextView txtTitle;
		TextView txtTime;
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

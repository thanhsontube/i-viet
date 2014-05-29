package com.android.iviet.search;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.iviet.R;

public class SearchAdapter extends ArrayAdapter<SearchDto> {
	private List<SearchDto> mList;
	private Context mContext;

	public SearchAdapter(Context context, List<SearchDto> list) {
		super(context, 0, list);
		mContext = context;
		mList = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.search_row, parent, false);
			holder = new Holder();
			holder.txtTitle = (TextView) v.findViewWithTag("title");
			holder.txtDetail = (TextView) v.findViewWithTag("detail");
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		final SearchDto dto = mList.get(position);
		holder.txtTitle.setText(dto.getTitle());
		holder.txtDetail.setText(dto.getSnapshot_content());
		if((position & 1) == 0){
			v.setBackgroundResource(R.drawable.btn_common_selector1);
		}else{
			v.setBackgroundResource(R.drawable.btn_common_selector2);
		}
		return v;
	}

	public void setData(List<SearchDto> objects) {
		mList.clear();
		mList.addAll(objects);
		notifyDataSetChanged();
	}

	static class Holder {
		TextView txtTitle;
		TextView txtDetail;
	}
}

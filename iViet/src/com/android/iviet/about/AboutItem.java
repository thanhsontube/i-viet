package com.android.iviet.about;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem;

public class AboutItem extends DrawerItem<Integer> {

	protected AboutItem(int icon, CharSequence title, CharSequence id, CharSequence extra, Integer param) {
		super(icon, title, id, extra, param);
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.about_row, parent, false);
			convertView.setTag(holder);

			holder.icon = (ImageView) convertView.findViewWithTag("icon");
			holder.title = (TextView) convertView.findViewWithTag("title");

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		holder.icon.setImageResource(icon);
		if (title != null) {
			holder.title.setText(title);
			holder.title.setVisibility(View.VISIBLE);
		} else {
			holder.title.setVisibility(View.GONE);
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView title;
	}

}

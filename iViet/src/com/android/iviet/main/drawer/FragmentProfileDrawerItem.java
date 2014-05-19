package com.android.iviet.main.drawer;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem;
import com.android.iviet.utils.FilterLog;

public class FragmentProfileDrawerItem extends DrawerItem<Fragment> {
	private static final String TAG = "FragmentProfileDrawerItem";
	FilterLog log = new FilterLog(TAG);


	protected FragmentProfileDrawerItem(int icon, CharSequence title, CharSequence id, CharSequence extra, Fragment param) {
		super(icon, title, id, extra, param);
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.fragment_profile_drawer_item, parent, false);
			convertView.setTag(holder);

			holder.imgProfile = (ImageView) convertView.findViewWithTag("img_button_profile");
			holder.imgNew = (ImageView) convertView.findViewWithTag("img_button_new");
			holder.imgAvatar = (ImageView) convertView.findViewWithTag("img_hexagon");
			holder.name = (TextView) convertView.findViewWithTag("drawer_profile_name");

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		if (title != null) {
//			holder.name.setText(title);
//			holder.name.setVisibility(View.VISIBLE);
//		} else {
//			holder.name.setVisibility(View.GONE);
//		}
		
		holder.imgProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.imgProfile.setOnClickListener");
			}
		});
		
		holder.imgNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.imgNew.setOnClickListener");
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView imgProfile;
		ImageView imgNew;
		ImageView imgAvatar;
		TextView name;
	}


}

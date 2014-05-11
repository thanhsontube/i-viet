package com.android.iviet.utils;

import android.app.ActionBar;
import android.view.View;
import android.widget.TextView;

public class ActionBarUtils {
	
	private static final FilterLog log = new FilterLog(ActionBarUtils.class.getSimpleName());

	public static void setTitle(ActionBar actionBar, CharSequence title) {
		if (actionBar == null) {
			return;
		}
		final View v = actionBar.getCustomView();
		if (v != null) {
			log.v("customView: " + v);
			final TextView text = (TextView) v.findViewById(android.R.id.title);
			if (text != null) {
				log.v("set title to TextView: " + title);
				text.setText(title);
				return;
			}
		}
		log.v("actionBar.setTitle: " + v);
		actionBar.setTitle(title);
		actionBar.setSubtitle(null);
		actionBar.show();
	}
}

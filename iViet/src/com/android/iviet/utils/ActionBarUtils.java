package com.android.iviet.utils;

import android.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.iviet.R;

public class ActionBarUtils {
	
	private static final FilterLog log = new FilterLog(ActionBarUtils.class.getSimpleName());

	public static void setTitleDefault(ActionBar actionBar, CharSequence title) {
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
	
	public static void setTitle(ActionBar actionBar, CharSequence title) {
		if (actionBar == null) {
			return;
		}
		final View v = actionBar.getCustomView();
		if (v != null) {
			final TextView text = (TextView) v.findViewById(R.id.actionbar_title);
			if (text != null) {
				text.setText(title);
				return;
			}
		}
		actionBar.show();
	}
	
	public static void update( ActionBar actionBar, int position, CharSequence title) {
		if (actionBar == null) {
			return;
		}
		final View v = actionBar.getCustomView();
		if (v != null) {
			log.v("customView: " + v);
			
			ImageView dot1 = (ImageView) v.findViewById(R.id.dot1);
			ImageView dot2 = (ImageView) v.findViewById(R.id.dot2);
			ImageView dot3 = (ImageView) v.findViewById(R.id.dot3);
			switch (position) {
			case 0:
				dot1.setImageResource(R.drawable.page_selected);
				dot2.setImageResource(R.drawable.page_deselected);
				dot3.setImageResource(R.drawable.page_deselected);
				break;
			case 1:
				dot1.setImageResource(R.drawable.page_deselected);
				dot2.setImageResource(R.drawable.page_selected);
				dot3.setImageResource(R.drawable.page_deselected);
				
				break;
			case 2:
				dot1.setImageResource(R.drawable.page_deselected);
				dot2.setImageResource(R.drawable.page_deselected);
				dot3.setImageResource(R.drawable.page_selected);
				
				break;

			default:
				break;
			}
			final TextView text = (TextView) v.findViewById(R.id.actionbar_title);
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
	
	public static void hideChat(ActionBar actionBar, boolean isHide) {
		if (actionBar == null) {
			return;
		}
		final View v = actionBar.getCustomView();
		if (v != null) {
			final ImageView imgChat = (ImageView) v.findViewById(R.id.img_chat);
			if (isHide) {
				imgChat.setVisibility(View.GONE);
			} else {
				imgChat.setVisibility(View.VISIBLE);
			}
		}
		actionBar.show();
	}
	
	public static void hideDot(ActionBar actionBar, boolean isHide) {
		if (actionBar == null) {
			return;
		}
		final View v = actionBar.getCustomView();
		if (v != null) {
			final View viewDot = v.findViewById(R.id.actionbar_dot);
			if (isHide) {
				viewDot.setVisibility(View.GONE);
			} else {
				viewDot.setVisibility(View.VISIBLE);
			}
		}
		actionBar.show();
	}
}

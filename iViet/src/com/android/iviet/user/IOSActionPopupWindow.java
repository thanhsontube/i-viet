package com.android.iviet.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.android.iviet.R;

public class IOSActionPopupWindow extends PopupWindow {

	public IOSActionPopupWindow() {
		super();
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
		// TODO Auto-generated constructor stub
		init();
	}

	public IOSActionPopupWindow(View contentView) {
		super(contentView);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
//		setAnimationStyle(R.style.IOSPopupWindowAnimation);
	}

	@Override
	public void setContentView(View contentView) {
		// TODO Auto-generated method stub
		super.setContentView(contentView);
	}

	public void show(View parent) {
		super.showAtLocation(parent, Gravity.BOTTOM, 0,0);
	}

}

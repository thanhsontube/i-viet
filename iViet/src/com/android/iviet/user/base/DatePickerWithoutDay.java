package com.android.iviet.user.base;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerWithoutDay extends DatePicker {

	public DatePickerWithoutDay(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public DatePickerWithoutDay(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public DatePickerWithoutDay(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setCalendarViewShown(false);
		findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
	}
}
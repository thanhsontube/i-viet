package com.android.iviet.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;

public class AlertDialogUtils {
	public static final void getIOSActionDialog(Context context, View view, final IOSActionBarCallBack callBack) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		view.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		try {
			((ImageButton) view.findViewWithTag("close")).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			((ImageButton) view.findViewWithTag("ok")).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (callBack != null) {
						callBack.onOK(dialog);
					}
				}
			});
		} catch (Exception e) {
		}

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.gravity = Gravity.BOTTOM;
		params.horizontalMargin = 0;
		params.verticalMargin = 0;
		params.width = LayoutParams.MATCH_PARENT;
		dialog.show();
	}

	
	public static final AlertDialog getIOSDialog(Context context, View view, final IOSActionBarCallBack callBack) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		view.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		try {
			((ImageButton) view.findViewWithTag("close")).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			((ImageButton) view.findViewWithTag("ok")).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (callBack != null) {
						callBack.onOK(dialog);
					}
				}
			});
		} catch (Exception e) {
		}

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.gravity = Gravity.BOTTOM;
		params.horizontalMargin = 0;
		params.verticalMargin = 0;
		params.width = LayoutParams.MATCH_PARENT;
		return dialog;
	}

	public interface IOSActionBarCallBack {
		void onOK(AlertDialog alertDialog);
	}

}

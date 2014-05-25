package com.android.iviet.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.iviet.R;

public class ReportDialog extends DialogFragment {
	ReportDialogListener listener;

	public interface ReportDialogListener {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_report);

		Button btnClose = (Button) dialog.findViewById(R.id.dialog_report_cancel);
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}

	public void setOnAddPictureDialogListener(ReportDialogListener listener) {
		this.listener = listener;
	}
}

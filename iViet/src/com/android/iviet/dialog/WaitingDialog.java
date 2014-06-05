package com.android.iviet.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import com.android.iviet.R;

public class WaitingDialog extends DialogFragment {
	IWaitingDialog listener;

	public interface IWaitingDialog {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.waiting);

//		Button btnClose = (Button) dialog.findViewById(R.id.dialog_report_cancel);
//		btnClose.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
		return dialog;
	}

	public void setOnAddPictureDialogListener(IWaitingDialog listener) {
		this.listener = listener;
	}
}

package com.android.iviet.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.iviet.R;

public class AddPictureDialog extends DialogFragment {
	AddPictureDialogListener listener;

	public interface AddPictureDialogListener {
		void onAddPictureDialogOK();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_add_image);

		Button btnOk = (Button) dialog.findViewById(R.id.dialog_add_picture);
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onAddPictureDialogOK();
				}
			}
		});

		Button btnClose = (Button) dialog.findViewById(R.id.dialog_cancel_add_image);
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}

	public void setOnAddPictureDialogListener(AddPictureDialogListener listener) {
		this.listener = listener;
	}
}

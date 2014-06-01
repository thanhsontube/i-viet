package com.android.iviet.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.utils.AlertDialogUtils;
import com.android.iviet.utils.AlertDialogUtils.IOSActionBarCallBack;

public class EditProfileFragment extends BaseFragment implements OnClickListener {
	private OverScrollView mRootView;
	private TextView mTvBirthday;
	private TextView mTvNationality;
	// private EditText mEdtDiary;
	private EditText mEdtUniversity;
	private EditText mEdtUniversityStart;
	private EditText mEdtUniversityEnd;
	private EditText mEdtUniversityMajor;
	private EditText mEdtUniversityCertificate;
	// private EditText mEdtUniversityDescripton;
	private EditText mEdtCompany;
	private EditText mEdtCompanyStart;
	private EditText mEdtCompanyEnd;
	private EditText mEdtCompanyPosition;

	// private EditText mEdtCompanyDescription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		mRootView = (OverScrollView) inflater.inflate(R.layout.fragment_edit_profile, container, false);
		((Button) mRootView.findViewById(R.id.editprofile_btn_personal_function)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_personal_save)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_diary_clear)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_diary_save)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_education_clear)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_education_save)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_experience_clear)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_experience_save)).setOnClickListener(this);
		((Button) mRootView.findViewById(R.id.editprofile_btn_back_to_close)).setOnClickListener(this);
		mTvBirthday = (TextView) mRootView.findViewById(R.id.editprofile_edt_birthday);
		mTvNationality = (TextView) mRootView.findViewById(R.id.editprofile_edt_nationality);
		mEdtUniversity = (EditText) mRootView.findViewById(R.id.editprofile_edt_university);
		mEdtUniversityStart = (EditText) mRootView.findViewById(R.id.editprofile_edt_universty_start_day);
		mEdtUniversityEnd = (EditText) mRootView.findViewById(R.id.editprofile_edt_universty_end_day);
		mEdtUniversityMajor = (EditText) mRootView.findViewById(R.id.editprofile_edt_university_major);
		mEdtUniversityCertificate = (EditText) mRootView.findViewById(R.id.editprofile_edt_certi);
		mEdtCompany = (EditText) mRootView.findViewById(R.id.editprofile_edt_company);
		mEdtCompanyStart = (EditText) mRootView.findViewById(R.id.editprofile_edt_experience_start);
		mEdtCompanyEnd = (EditText) mRootView.findViewById(R.id.editprofile_edt_experience_end_day);
		mEdtCompanyPosition = (EditText) mRootView.findViewById(R.id.editprofile_edt_position);
		mTvBirthday.setOnClickListener(this);
		mTvNationality.setOnClickListener(this);
		mEdtUniversity.setOnClickListener(this);
		mEdtUniversityStart.setOnClickListener(this);
		mEdtUniversityEnd.setOnClickListener(this);
		mEdtUniversityMajor.setOnClickListener(this);
		mEdtUniversityCertificate.setOnClickListener(this);
		mEdtCompany.setOnClickListener(this);
		mEdtCompanyStart.setOnClickListener(this);
		mEdtCompanyEnd.setOnClickListener(this);
		mEdtCompanyPosition.setOnClickListener(this);

		return mRootView;
	}

	@Override
	protected String generateTitle() {
		return "Chỉnh sửa cá nhân";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editprofile_btn_personal_function:
			View personalView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_personal_dialog_function, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), personalView, null);
			break;
		case R.id.editprofile_btn_diary_clear:
			View diaryView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_diary_dialog_clear, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), diaryView, null);
			break;
		case R.id.editprofile_btn_education_clear:
			View educationView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_dialog_clear, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), educationView, null);
			break;
		case R.id.editprofile_btn_experience_clear:
			View experienceView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_experience_dialog_clear, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), experienceView, null);
			break;
		case R.id.editprofile_btn_back_to_close:
			mRootView.fullScroll(ScrollView.FOCUS_UP);
			break;
		case R.id.editprofile_edt_birthday:
			View birthdayView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_birthday, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), birthdayView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;
		default:
			break;
		}

	}
}

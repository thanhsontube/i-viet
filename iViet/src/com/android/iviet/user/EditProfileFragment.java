package com.android.iviet.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.user.base.OverScrollView;
import com.android.iviet.user.country.Country;
import com.android.iviet.user.country.CountryListAdapter;
import com.android.iviet.utils.AlertDialogUtils;
import com.android.iviet.utils.AlertDialogUtils.IOSActionBarCallBack;

public class EditProfileFragment extends BaseFragment implements OnClickListener {
	private OverScrollView mRootView;
	private TextView mTvBirthday;
	private TextView mTvNationality;
	// private EditText mEdtDiary;
	private EditText mEdtUniversity;
	private TextView mTvUniversityStart;
	private TextView mTvUniversityEnd;
	private EditText mEdtUniversityMajor;
	private TextView mTvUniversityCertificate;
	// private EditText mEdtUniversityDescripton;
	private EditText mEdtCompany;
	private TextView mTvCompanyStart;
	private TextView mTvCompanyEnd;
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
		mTvUniversityStart = (TextView) mRootView.findViewById(R.id.editprofile_edt_universty_start_day);
		mTvUniversityEnd = (TextView) mRootView.findViewById(R.id.editprofile_edt_universty_end_day);
		mEdtUniversityMajor = (EditText) mRootView.findViewById(R.id.editprofile_edt_university_major);
		mTvUniversityCertificate = (TextView) mRootView.findViewById(R.id.editprofile_edt_certi);
		mEdtCompany = (EditText) mRootView.findViewById(R.id.editprofile_edt_company);
		mTvCompanyStart = (TextView) mRootView.findViewById(R.id.editprofile_edt_experience_start);
		mTvCompanyEnd = (TextView) mRootView.findViewById(R.id.editprofile_edt_experience_end_day);
		mEdtCompanyPosition = (EditText) mRootView.findViewById(R.id.editprofile_edt_position);
		mTvBirthday.setOnClickListener(this);
		mTvNationality.setOnClickListener(this);
		mEdtUniversity.setOnClickListener(this);
		mTvUniversityStart.setOnClickListener(this);
		mTvUniversityEnd.setOnClickListener(this);
		mEdtUniversityMajor.setOnClickListener(this);
		mTvUniversityCertificate.setOnClickListener(this);
		mEdtCompany.setOnClickListener(this);
		mTvCompanyStart.setOnClickListener(this);
		mTvCompanyEnd.setOnClickListener(this);
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
		case R.id.editprofile_edt_nationality:
			View nationalityView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_country_dialog, null);
			ArrayList<Country> countries = getAllContries();
			ListView lvCountry = (ListView) nationalityView.findViewById(R.id.editprofile_lv_country);
			CountryListAdapter adapter = new CountryListAdapter(getActivity(), countries);
			lvCountry.setAdapter(adapter);
			Collections.sort(countries, new Comparator<Country>() {

				@Override
				public int compare(Country lhs, Country rhs) {
					return lhs.getName().compareToIgnoreCase(rhs.getName());
				}
			});
			AlertDialogUtils.getIOSActionDialog(getActivity(), nationalityView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;

		case R.id.editprofile_edt_universty_start_day:
			View startUniversityView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_start, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), startUniversityView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;
		case R.id.editprofile_edt_experience_start:
			View startExperienceView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_start, null);
			AlertDialogUtils.getIOSActionDialog(getActivity(), startExperienceView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;
		case R.id.editprofile_edt_universty_end_day:
			View endUniversityView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_end_function, null);
			Button openCalendar = (Button) endUniversityView.findViewWithTag("open");
			openCalendar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View endUniversityCalendar = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_start, null);
					AlertDialogUtils.getIOSActionDialog(getActivity(), endUniversityCalendar,
							new IOSActionBarCallBack() {

								@Override
								public void onOK(AlertDialog alertDialog) {
									// TODO Auto-generated method stub
									alertDialog.dismiss();
								}
							});
				}
			});
			AlertDialogUtils.getIOSActionDialog(getActivity(), endUniversityView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;
		case R.id.editprofile_edt_experience_end_day:
			View endExperienceView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_end_function, null);
			Button openExperienceCalendar = (Button) endExperienceView.findViewWithTag("open");
			openExperienceCalendar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					View endUniversityCalendar = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_education_start, null);
					AlertDialogUtils.getIOSActionDialog(getActivity(), endUniversityCalendar,
							new IOSActionBarCallBack() {

								@Override
								public void onOK(AlertDialog alertDialog) {
									// TODO Auto-generated method stub
									alertDialog.dismiss();
								}
							});
				}
			});
			AlertDialogUtils.getIOSActionDialog(getActivity(), endExperienceView, new IOSActionBarCallBack() {

				@Override
				public void onOK(AlertDialog alertDialog) {
					// TODO Auto-generated method stub
					alertDialog.dismiss();
				}
			});
			break;
		case R.id.editprofile_edt_certi:
			View certificateView = ((LayoutInflater) getActivity().getBaseContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.editprofile_certificate_dialog, null);
			ArrayList<String> certificates = getAllCertficates();
			ListView lvCertificate = (ListView) certificateView.findViewById(R.id.editprofile_lv_certificate);
			ArrayAdapter<String> certiAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
					certificates);
			lvCertificate.setAdapter(certiAdapter);
			AlertDialogUtils.getIOSActionDialog(getActivity(), certificateView, new IOSActionBarCallBack() {

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

	private ArrayList<Country> getAllContries() {
		Locale[] locales = Locale.getAvailableLocales();
		ArrayList<Country> countries = new ArrayList<Country>();
		ArrayList<String> tempCountries = new ArrayList<>();
		for (Locale locale : locales) {
			String country = locale.getDisplayCountry();
			String code = locale.getCountry();
			if (country.trim().length() > 0 && !tempCountries.contains(country) && !isNumeric(code)) {
				tempCountries.add(country);
				countries.add(new Country(code, country));
			}
		}
		return countries;

	}

	private ArrayList<String> getAllCertficates() {
		ArrayList<String> certificates = new ArrayList<String>();
		certificates.add("Khác");
		certificates.add("Cấp 1");
		certificates.add("Cấp 2");
		certificates.add("Cấp 3");
		certificates.add("Trung Cấp");
		certificates.add("Cao Đẳng");
		certificates.add("Đạ Học");
		certificates.add("Thạc Sĩ");
		certificates.add("Tiến Sĩ /  Bác Sĩ");
		certificates.add("Sau Tiến Sĩ");
		return certificates;

	}

	private static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}

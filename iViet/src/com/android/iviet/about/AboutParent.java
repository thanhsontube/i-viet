package com.android.iviet.about;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.iviet.R;
import com.android.iviet.utils.FilterLog;

public class AboutParent implements AboutIParent {
	private static final String TAG = "AboutParent";
	FilterLog log = new FilterLog(TAG);
	private Context mContext;

	public AboutParent(Context context) {
		mContext = context;
	}

	@Override
	public List<AboutItem> generate() {
		List<AboutItem> list = new ArrayList<>();
		list.add(new AboutItem(0, "Điều khoản về iViet", null, null, 0));
		list.add(new AboutItem(0, "Quy định đặt câu hỏi", null, null, 1));
		return list;
	}

	@Override
	public boolean isUpdate() {
		return false;
	}

	@Override
	public View getHeader() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View header = inflater.inflate(R.layout.about_row, null);
		View img = header.findViewWithTag("icon");
		img.setVisibility(View.GONE);
		TextView txt = (TextView) header.findViewWithTag("about_version");

		try {
			String versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
			log.d("log>>>" + "versionName:" + versionName);
			txt.setText(versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return header;
	}

}

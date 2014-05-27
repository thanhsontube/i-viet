package com.android.iviet.user;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

public class UserProfileFragment extends Fragment {
	
	private static final String TAG = "UserProfileFragment";
	private ActionBar mActionBar;
	FilterLog log = new FilterLog(TAG);
	ProfileAdapter adapter;
	List<ProfileDto> list = new ArrayList<ProfileDto>();
	
	private GridView mGridView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
		// Set up the action bar.
		mActionBar = getActivity().getActionBar();
		ActionBarUtils.setTitle(mActionBar, "Thông tin");
		
		mGridView = (GridView) rootView.findViewById(R.id.profile_gridview);
		list.add(new ProfileDto());
		list.add(new ProfileDto());
		list.add(new ProfileDto());
		list.add(new ProfileDto());
		list.add(new ProfileDto());
		list.add(new ProfileDto());
		
		adapter = new ProfileAdapter(getActivity(), list);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
		});
		return rootView;
	}
	
	
}

package com.android.iviet.user;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.utils.FilterLog;

public class UserProfileFragment extends BaseFragment {
	
	private static final String TAG = "UserProfileFragment";
	FilterLog log = new FilterLog(TAG);
	ProfileAdapter adapter;
	List<ProfileDto> list = new ArrayList<ProfileDto>();
	
	private GridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
		mGridView = (GridView) rootView.findViewById(R.id.profile_gridview);
		list.add(new ProfileDto(R.drawable.favorite_button_selector));
		list.add(new ProfileDto(R.drawable.question_button_selector));
		list.add(new ProfileDto(R.drawable.answer_button_selector));
		list.add(new ProfileDto(R.drawable.shape_icon));
		list.add(new ProfileDto(R.drawable.timeline_button_selector));
		list.add(new ProfileDto(R.drawable.shape_icon));
		
		adapter = new ProfileAdapter(getActivity(), list);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
		});
		return rootView;
	}

	@Override
    public String generateTitle() {
	    return "Thông tin";
    }
	
	
}

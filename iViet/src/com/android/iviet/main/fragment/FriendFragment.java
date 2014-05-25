package com.android.iviet.main.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.connection.BasicAccessPathGenerator;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.MainLoader;
import com.android.iviet.main.adapter.MainBaseAdapter;
import com.android.iviet.main.dto.DataRootDto;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.MainFragment.IMainFragmentListener;
import com.android.iviet.utils.FilterLog;

public class FriendFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.friend_fragment, container, false);
		return v;

	}
	
}

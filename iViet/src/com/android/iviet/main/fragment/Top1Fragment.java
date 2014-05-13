package com.android.iviet.main.fragment;

import com.android.iviet.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Top1Fragment extends Fragment {
	private ListView listview;
	private ITop1FragmentListener mListener;
	
	public static interface ITop1FragmentListener {
		void onTop1AvatarClicked();
		void onTop1ContentClicked();
	}
	/**
	 * get data from server
	 * @author sonnt
	 *
	 */
	public interface Controller {
		void load();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.top1_fragment, container, false);
		return v;

	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ITop1FragmentListener) {
			mListener = (ITop1FragmentListener) activity;
		}
	}
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}

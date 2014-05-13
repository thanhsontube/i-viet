package com.android.iviet.welcome.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.iviet.R;

public class SlideFragment extends Fragment {
	public static final String SLIDE_ORDER = "SLIDE_ORDER";
	private String mStrTopic;
	private String mStrMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
		int order = 0;
		if (bundle != null) {
			order = bundle.getInt(SLIDE_ORDER, 0) + 1;
		}
		Resources resources = getResources();
		String pack = getActivity().getPackageName();
		mStrTopic = resources.getString(resources.getIdentifier(
				"welcome_topic_" + order, "string", pack));
		mStrMessage = resources.getString(resources.getIdentifier(
				"welcome_message_" + order, "string", pack));
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.slide_fragment, container,
				false);
		((TextView) rootView.findViewById(R.id.welcome_tv_topic))
				.setText(mStrTopic);
		((TextView) rootView.findViewById(R.id.welcome_tv_message))
				.setText(mStrMessage);
		return rootView;

	}

}

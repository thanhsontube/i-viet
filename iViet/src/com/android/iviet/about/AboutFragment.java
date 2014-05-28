package com.android.iviet.about;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;

public class AboutFragment extends Fragment {
	private ListView mListView;
	private AboutAdapter mAboutAdapter;
	private List<AboutItem> list;
	private IVietApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    app = (IVietApplication) getActivity().getApplication();
	    list = app.getAboutParent().generate();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.about_fragment, container, false);
		mListView = (ListView) v.findViewById(R.id.about_listview);
		mAboutAdapter = new AboutAdapter(list);
//		View header = inflater.inflate(R.layout.about_row, null);
		mListView.addHeaderView(app.getAboutParent().getHeader());
		mListView.setAdapter(mAboutAdapter);
		return v;
	}
	
	class AboutAdapter extends ArrayAdapter<AboutItem> {
		public AboutAdapter (List<AboutItem> list) {
			super(getActivity(), 0, list);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View v = getItem(position).getView(getLayoutInflater(getArguments()), convertView, parent);
		    return v;
		}
	}
}

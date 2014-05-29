package com.android.iviet.about;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.utils.FilterLog;

public class AboutFragment extends BaseFragment implements OnItemClickListener {
	private static final String TAG = "AboutFragment";
	private ListView mListView;
	private AboutAdapter mAboutAdapter;
	private List<AboutItem> list;
	private IVietApplication app;
	private IAboutFragment listener;

	FilterLog log = new FilterLog(TAG);

	public interface IAboutFragment {
		public void onAboutFragmentTerm();

		public void onAboutFragmentRuleOfAsk();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof IAboutFragment) {
			listener = (IAboutFragment) activity;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (IVietApplication) getActivity().getApplication();
		list = app.getAboutParent().generate();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.about_fragment, container, false);
		mListView = (ListView) v.findViewById(R.id.about_listview);
		mAboutAdapter = new AboutAdapter(list);
		mListView.addHeaderView(app.getAboutParent().getHeader(), null, false);
		mListView.setAdapter(mAboutAdapter);
		mListView.setOnItemClickListener(this);
		return v;
	}

	class AboutAdapter extends ArrayAdapter<AboutItem> {
		public AboutAdapter(List<AboutItem> list) {
			super(getActivity(), 0, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = getItem(position).getView(getLayoutInflater(getArguments()), convertView, parent);
			return v;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		AboutItem item = (AboutItem) mListView.getAdapter().getItem(position);
		int pos = item.getParam();
		log.d("log>>>" + "pos:" + pos + ";position:" + position);
		switch (pos) {
		case 0:
			log.d("log>>>" + "Dieu Khoan");
			if (listener != null) {
				listener.onAboutFragmentTerm();
			}
			break;
		case 1:
			log.d("log>>>" + "rule of ask");
			if (listener != null) {
				listener.onAboutFragmentRuleOfAsk();
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected String generateTitle() {
		return "Về iViet";
	}
}

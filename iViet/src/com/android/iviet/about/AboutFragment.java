package com.android.iviet.about;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.FilterLog;

public class AboutFragment extends Fragment implements OnItemClickListener {
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (IVietApplication) getActivity().getApplication();
		list = app.getAboutParent().generate();
		ActionBarUtils.setTitle(getActivity().getActionBar(), "Về iViet");
		setHasOptionsMenu(true);
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

	MenuItem menuTemp;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menuTemp = menu.add(Menu.NONE, 8, Menu.NONE, "Chat123");
		menuTemp.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuTemp.setIcon(R.drawable.shape_icon);

		// menu.add(title)
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		menuTemp.setVisible(true);
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
}

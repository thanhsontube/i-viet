package com.android.iviet.search;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iviet.R;
import com.android.iviet.about.BaseFragment;

public class SearchFragment extends BaseFragment {
	
	private SearchView mSearchView;
	private ViewGroup mEmpty;
	private ListView mListView;

	@Override
    protected String generateTitle() {
	    return "Tìm kiếm";
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		View viewRoot = inflater.inflate(R.layout.search_fragment, container, false);
		mEmpty = (ViewGroup) viewRoot.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		
		mListView = (ListView) viewRoot.findViewById(R.id.search_listview);
	    return viewRoot;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	    
	    inflater.inflate(R.menu.menu_search, menu);

		SearchManager mSearchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
		ComponentName c = getActivity().getComponentName();
		SearchableInfo mSearchableInfo = mSearchManager.getSearchableInfo(c);
		MenuItem mMenuItem = menu.findItem(R.id.action_search);
	    mSearchView = (SearchView) mMenuItem.getActionView();
		mSearchView.setSearchableInfo(mSearchableInfo);
		mSearchView.setIconifiedByDefault(true);
		mSearchView.setQueryHint("Tìm kiếm");
		
		ImageView searchIcon = (ImageView) mSearchView.findViewById(getResources().getIdentifier("android:id/search_button", null, null));
		searchIcon.setImageResource(R.drawable.ic_action_action_search); 
		
		ImageView closeIcon = (ImageView) mSearchView.findViewById(getResources().getIdentifier("android:id/search_close_btn",null,null));
		closeIcon.setImageResource(R.drawable.ic_action_content_remove); 

		TextView text = (TextView) mSearchView.findViewById(getResources().getIdentifier("android:id/search_src_text", null, null));
		text.setTextColor(Color.WHITE);
		text.setHintTextColor(Color.WHITE);
		
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
			@Override
			public boolean onQueryTextSubmit(String query) {
				mSearchView.clearFocus();
				Toast.makeText(getActivity(), "submit search", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    menuTransparent.setVisible(false);
	}
	
}

package com.android.iviet.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.SearchLoader;
import com.android.iviet.utils.FilterLog;

public class SearchFragment extends BaseFragment {
	
	private static final String TAG = "SearchFragment";
	private SearchView mSearchView;
	private ViewGroup mEmpty;
	private ListView mListView;
	FilterLog log = new FilterLog(TAG);
	private ContentManager mContentManager;
	private PathAccess mPathAccess;
	private SearchAdapter adapter;
	private List<SearchDto> mList;

	@Override
    protected String generateTitle() {
	    return getString(R.string.title_search);
    }
	
	private ISearchFragmentListener mListener;

	public static interface ISearchFragmentListener {
		void onISearchFragmentListenerItemClicked(SearchFragment fragment, SearchDto entity);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ISearchFragmentListener) {
			mListener = (ISearchFragmentListener) activity;
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
	    IVietApplication app = (IVietApplication) getActivity().getApplication();
		mContentManager = app.getContentManager();
		mPathAccess = (PathAccess) app.getPathAccess();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		View viewRoot = inflater.inflate(R.layout.search_fragment, container, false);
		mEmpty = (ViewGroup) viewRoot.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.GONE);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		
		mListView = (ListView) viewRoot.findViewById(R.id.search_listview);
		mList = new ArrayList<SearchDto>();
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
//		mList.add(new SearchDto());
		adapter = new SearchAdapter(getActivity(), mList);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            if (mListener != null) {
	            	mListener.onISearchFragmentListenerItemClicked(SearchFragment.this, mList.get(position));
	            }
	            
            }
		});
	    return viewRoot;
	}
	ImageView closeIcon;
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
		mSearchView.setQueryHint(getActivity().getString(R.string.search_input_search));
		
		ImageView searchIcon = (ImageView) mSearchView.findViewById(getResources().getIdentifier("android:id/search_button", null, null));
		searchIcon.setImageResource(R.drawable.ic_action_action_search); 
		
		closeIcon = (ImageView) mSearchView.findViewById(getResources().getIdentifier("android:id/search_close_btn",null,null));
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
				mController.load();
				return true;
			}
		});
	}
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    menuTransparent.setVisible(false);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
//	    mController.load();
	}
	
	public interface Controller {
		void load();
	}
	
	private final Controller mController = new Controller() {
		
		@Override
		public void load() {
			log.d("log>>>" + "LOAD:" + mPathAccess.search());
			HttpGet httpGet = new HttpGet(mPathAccess.search());
			mContentManager.load(new SearchLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(List<SearchDto> entity) {
					log.d("log>>>" + "onContentLoaderSucceed");
					mEmpty.setVisibility(View.GONE);
					log.d("log>>>" + "size:" + entity.size());
					adapter.setData(entity);
					
					
				}
				
				@Override
				public void onContentLoaderStart() {
					mEmpty.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onContentLoaderFailed(Throwable e) {
					mEmpty.setVisibility(View.GONE);
					log.e("log>>>" + "onContentLoaderFailed:" + e.toString());
				}
			});
			
		}
	};

	@Override
    public void onHiddenChanged(boolean hidden) {
	    super.onHiddenChanged(hidden);
	    mSearchView.clearFocus();
    }
	
	
	
	
}

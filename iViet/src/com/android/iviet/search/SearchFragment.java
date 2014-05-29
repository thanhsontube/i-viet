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
import android.widget.Toast;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.MainLoader;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.main.dto.DataRootDto;
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
	    return "Tìm kiếm";
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
		inflater.inflate(R.layout.waiting, mEmpty, true);
		
		mListView = (ListView) viewRoot.findViewById(R.id.search_listview);
		mList = new ArrayList<SearchDto>();
		mList.add(new SearchDto());
		mList.add(new SearchDto());
		mList.add(new SearchDto());
		mList.add(new SearchDto());
		mList.add(new SearchDto());
		mList.add(new SearchDto());
		mList.add(new SearchDto());
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
	
	@Override
	public void onResume() {
	    super.onResume();
	    mController.load();
	}
	
	public interface Controller {
		void load();
	}
	
	private final Controller mController = new Controller() {
		
		@Override
		public void load() {
			log.d("log>>>" + "LOAD:" + mPathAccess.seach());
			HttpGet httpGet = new HttpGet(mPathAccess.seach());
			mContentManager.load(new MainLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(DataRootDto entity) {
					log.d("log>>>" + "onContentLoaderSucceed");
					mEmpty.setVisibility(View.GONE);
					log.d("log>>>" + "size:" + entity.getList().size());
//					adapter.setData(entity.getList());
					
					
				}
				
				@Override
				public void onContentLoaderStart() {
					// TODO Auto-generated method stub
					log.d("log>>>" + "onContentLoaderStart");
					
				}
				
				@Override
				public void onContentLoaderFailed(Throwable e) {
					// TODO Auto-generated method stub
					log.d("log>>>" + "onContentLoaderFailed");
					
				}
				
			});
		}
	};
	
	
}

package com.android.iviet.friend;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.FriendLoader;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.utils.FilterLog;

public class FriendFragment extends BaseFragment {

	private static final String TAG = "NewFeedFragment";
	private ViewGroup mEmpty;
	private ListView mListView;
	FilterLog log = new FilterLog(TAG);
	private ContentManager mContentManager;
	private PathAccess mPathAccess;
	private FriendAdapter adapter;
	private List<FriendDto> mList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    IVietApplication app = (IVietApplication) getActivity().getApplication();
		mContentManager = app.getContentManager();
		mPathAccess = (PathAccess) app.getPathAccess();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View viewRoot = inflater.inflate(R.layout.friend_fragment, container, false);
		mEmpty = (ViewGroup) viewRoot.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.VISIBLE);
		inflater.inflate(R.layout.waiting, mEmpty, true);

		mListView = (ListView) viewRoot.findViewById(R.id.chat_lv_friend_list);
		mList = new ArrayList<FriendDto>();
		adapter = new FriendAdapter(getActivity(), mList);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		return viewRoot;

	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    menuTransparent.setVisible(false);
	}
	
	

	@Override
	protected String generateTitle() {
		return null;
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
			log.d("log>>>" + "LOAD:" + mPathAccess.friendList());
			HttpGet httpGet = new HttpGet(mPathAccess.friendList());
			mContentManager.load(new FriendLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(List<FriendDto> entity) {
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

}

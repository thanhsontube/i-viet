package com.android.iviet.newfeed;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.connection.BaseObject;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.NotificaitonLoader;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.RootLoader;
import com.android.iviet.main.adapter.MainBaseAdapter;
import com.android.iviet.main.dto.RootDto;
import com.android.iviet.main.fragment.Top1Fragment;
import com.android.iviet.utils.FilterLog;

public class AnswerFragment extends BaseFragment {
	
	private static final String TAG = "NewFeedFragment";
	private ViewGroup mEmpty;
	private ListView mListView;
	FilterLog log = new FilterLog(TAG);
	private ContentManager mContentManager;
	private PathAccess mPathAccess;
	private MainBaseAdapter adapter;
	private List<BaseObject> mList;


	@Override
	protected String generateTitle() {
		return getString(R.string.title_answer);
	}
	
	private INotiFragmentListener mListener;

	public static interface INotiFragmentListener {
		void onISearchFragmentListenerItemClicked(AnswerFragment fragment, NotiDto entity);
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof INotiFragmentListener) {
			mListener = (INotiFragmentListener) activity;
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
		
		
		View viewRoot = inflater.inflate(R.layout.noti_fragmnt, container, false);
		mEmpty = (ViewGroup) viewRoot.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.VISIBLE);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		
		mListView = (ListView) viewRoot.findViewById(R.id.noti_listview);
		mList = new ArrayList<BaseObject>();
		adapter = new MainBaseAdapter(getActivity(), mList, this);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            if (mListener != null) {
//	            	mListener.onISearchFragmentListenerItemClicked(AnswerFragment.this, mList.get(position));
	            }
	            
            }
		});
	    return viewRoot;
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
			log.d("log>>>" + "LOAD:" + mPathAccess.notificaiton());
			HttpGet httpGet = new HttpGet(mPathAccess.notificaiton());
			mContentManager.load(new RootLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(RootDto entity) {
					List<BaseObject> listBase = entity.getNewestDto().getList();
					log.d("log>>>" + "onContentLoaderSucceed listBase new:" + listBase);
					mEmpty.setVisibility(View.GONE);
					adapter.setData(entity.getNewestDto().getList());
				}
				
				@Override
				public void onContentLoaderStart() {
					log.d("log>>>" + "onContentLoaderStart");
					
				}
				
				@Override
				public void onContentLoaderFailed(Throwable e) {
					log.e("log>>>" + "onContentLoaderFailed");
					
				}
			});
			
			
			
			
//			mContentManager.load(new NotificaitonLoader(httpGet, false) {
//				
//				@Override
//				public void onContentLoaderSucceed(List<NotiDto> entity) {
//					log.d("log>>>" + "onContentLoaderSucceed");
//					mEmpty.setVisibility(View.GONE);
//					log.d("log>>>" + "size:" + entity.size());
//					adapter.setData(entity);
//					
//					
//				}
//				
//				@Override
//				public void onContentLoaderStart() {
//					mEmpty.setVisibility(View.VISIBLE);
//				}
//				
//				@Override
//				public void onContentLoaderFailed(Throwable e) {
//					mEmpty.setVisibility(View.GONE);
//					log.e("log>>>" + "onContentLoaderFailed:" + e.toString());
//				}
//			});
			
		}
	};


	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	    MenuItem menuDelete = menu.add(Menu.NONE, 8, Menu.NONE, "menudelete");
		menuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuDelete.setIcon(R.drawable.delete);
    }
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    menuTransparent.setVisible(false);
	    
	}
	
	
	
}

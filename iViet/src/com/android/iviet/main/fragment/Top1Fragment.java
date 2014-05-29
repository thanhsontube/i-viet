package com.android.iviet.main.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.MainLoader;
import com.android.iviet.main.adapter.MainBaseAdapter;
import com.android.iviet.main.dto.DataRootDto;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.MainFragment.IMainFragmentListener;
import com.android.iviet.utils.FilterLog;

public class Top1Fragment extends Fragment implements IMainFragmentListener{
	private static final String TAG = "Top1Fragment";
	private ListView listview;
	public ITop1FragmentListener mListener;
	private MainBaseAdapter adapter;
	private List<MainDto> list = new ArrayList<MainDto>();
	private ContentManager mContentManager;
	private PathAccess mPathAccess;
	FilterLog log = new FilterLog(TAG);
	private View mEmpty;
	
	public static interface ITop1FragmentListener {
		void onTop1AvatarClicked(Top1Fragment f, MainDto dto);
		void onTop1ContentClicked(Top1Fragment f, MainDto dto);
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
		listview = (ListView) v.findViewById(R.id.main_listview);
		adapter = new MainBaseAdapter(getActivity(), list, Top1Fragment.this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				log.v("log>>> " + "onItemClick mListener:" + mListener);
				Toast.makeText(getActivity(), "Row CLick", Toast.LENGTH_SHORT).show();
				if (mListener == null) {
					return;
				}
				MainDto item = (MainDto)adapter.getItemAtPosition(position);
				mListener.onTop1ContentClicked(Top1Fragment.this, item);
			}
		});
		
		mEmpty = v.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, (ViewGroup) mEmpty, true);
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
		IVietApplication app = (IVietApplication) getActivity().getApplication();
		mContentManager = app.getContentManager();
		mPathAccess = (PathAccess) app.getPathAccess();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mController.load();
	}
	private final Controller mController = new Controller() {
		
		@Override
		public void load() {
			log.d("log>>>" + "LOAD:" + mPathAccess.newest());
			HttpGet httpGet = new HttpGet(mPathAccess.newest());
			mContentManager.load(new MainLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(DataRootDto entity) {
					log.d("log>>>" + "onContentLoaderSucceed");
					mEmpty.setVisibility(View.GONE);
					// TODO Auto-generated method stub
					log.d("log>>>" + "size:" + entity.getList().size());
					adapter.setData(entity.getList());
					
					
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
	
	// interface of MAIN FRAGMENT
	@Override
    public void onIMainFragmentStart(MainFragment f, int i) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void onMainFragmentPageSelected(MainFragment main, Fragment selected) {
		log.d("log>>>" + "onMainFragmentPageSelected");
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void onMainFragmentPageDeSelected(MainFragment main, Fragment selected) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void onMainFragmentMenuChatSelected(MainFragment main) {
	    // TODO Auto-generated method stub
	    
    }
	
}

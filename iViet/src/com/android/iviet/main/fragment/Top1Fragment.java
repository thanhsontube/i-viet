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
import com.android.iviet.connection.BasicAccessPathGenerator;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.MainLoader;
import com.android.iviet.main.adapter.MainBaseAdapter;
import com.android.iviet.main.dto.DataRootDto;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.utils.FilterLog;

public class Top1Fragment extends Fragment {
	private static final String TAG = "Top1Fragment";
	private ListView listview;
	public ITop1FragmentListener mListener;
	private MainBaseAdapter adapter;
	private List<MainDto> list = new ArrayList<MainDto>();
	private ContentManager mContentManager;
	private BasicAccessPathGenerator mBasicAccessPathGenerator;
	FilterLog log = new FilterLog(TAG);
	private View empty;
	
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
				log.v("NECVN>>> " + "onItemClick mListener:" + mListener);
				Toast.makeText(getActivity(), "Row CLick", Toast.LENGTH_SHORT).show();
				if (mListener == null) {
					return;
				}
				MainDto item = (MainDto)adapter.getItemAtPosition(position);
				mListener.onTop1ContentClicked(Top1Fragment.this, item);
			}
		});
		
		empty = v.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, (ViewGroup) empty, true);
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
		mBasicAccessPathGenerator = app.getBasicAccessPathGenerator();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mController.load();
	}
	private final Controller mController = new Controller() {
		
		@Override
		public void load() {
			log.d("NECVN>>>" + "LOAD:" + mBasicAccessPathGenerator.newest());
			HttpGet httpGet = new HttpGet(mBasicAccessPathGenerator.newest());
			mContentManager.load(new MainLoader(httpGet, false) {
				
				@Override
				public void onContentLoaderSucceed(DataRootDto entity) {
					log.d("NECVN>>>" + "onContentLoaderSucceed");
					empty.setVisibility(View.GONE);
					// TODO Auto-generated method stub
					log.d("NECVN>>>" + "size:" + entity.getList().size());
					adapter.setData(entity.getList());
					
					
				}
				
				@Override
				public void onContentLoaderStart() {
					// TODO Auto-generated method stub
					log.d("NECVN>>>" + "onContentLoaderStart");
					
				}
				
				@Override
				public void onContentLoaderFailed(Throwable e) {
					// TODO Auto-generated method stub
					log.d("NECVN>>>" + "onContentLoaderFailed");
					
				}
				
			});
		}
	};
	
//	MainLoader mainLoader = new MainLoader(new HttpGet(mBasicAccessPathGenerator.newest()), false) {
//
//		@Override
//		public void onContentLoaderStart() {
//			log.d("NECVN>>>" + "onContentLoaderStart");
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onContentLoaderSucceed(MainDto entity) {
//			log.d("NECVN>>>" + "onContentLoaderSucceed");
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onContentLoaderFailed(Throwable e) {
//			log.d("NECVN>>>" + "onContentLoaderFailed");
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		protected MainDto handleStream(InputStream in) throws IOException {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	};
}

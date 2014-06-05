package com.android.iviet.main.fragment;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.iviet.IVietApplication;
import com.android.iviet.MsConst;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragment;
import com.android.iviet.connection.BaseObject;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.PathAccess;
import com.android.iviet.connection.RootLoader;
import com.android.iviet.main.adapter.MainBaseAdapter;
import com.android.iviet.main.dto.RootDto;
import com.android.iviet.utils.FilterLog;

abstract public class BaseTopFragment extends BaseFragment {
	private static final String TAG = "BaseTopFragment";
	private ListView listview;
	public IBaseTopFragmentListener mListener;
	private MainBaseAdapter adapter;
	private List<BaseObject> list = new ArrayList<BaseObject>();
	private ContentManager mContentManager;
	private PathAccess mPathAccess;
	FilterLog log = new FilterLog(TAG);
	private View mEmpty;

	public abstract int initType();

	public static interface IBaseTopFragmentListener {
		void onBaseTopAvatarClicked(BaseTopFragment f, BaseObject dto);

		void onBaseTopContentClicked(BaseTopFragment f, BaseObject dto);
	}

	/**
	 * get data from server
	 * 
	 * @author sonnt
	 * 
	 */
	public interface Controller {
		void load();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.top1_fragment, container, false);
		listview = (ListView) v.findViewById(R.id.main_listview);
		adapter = new MainBaseAdapter(getActivity(), list, BaseTopFragment.this);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				if (mListener == null) {
					return;
				}
				BaseObject item = (BaseObject) adapter.getItemAtPosition(position);
				mListener.onBaseTopContentClicked(BaseTopFragment.this, item);
			}
		});

		mEmpty = v.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, (ViewGroup) mEmpty, true);
		return v;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof IBaseTopFragmentListener) {
			mListener = (IBaseTopFragmentListener) activity;
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
			URI uri = null;
			switch (initType()) {
			case MsConst.TYPE_NEWEST:
				uri = mPathAccess.newest();
				break;
			case MsConst.TYPE_FEATURED:
				uri = mPathAccess.feature();
				break;
			case MsConst.TYPE_EVERYTHING:
				uri = mPathAccess.search();
				break;
			default:
				uri = mPathAccess.newest();
				break;
			}
			HttpGet httpGet = new HttpGet(uri);
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

		}
	};

}

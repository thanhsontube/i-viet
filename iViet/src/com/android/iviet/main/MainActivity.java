package com.android.iviet.main;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem;
import com.android.iviet.main.drawer.FragmentChangeDrawerItem;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.MainFragment;
import com.android.iviet.main.fragment.Top1Fragment;
import com.android.iviet.main.fragment.Top1Fragment.ITop1FragmentListener;
import com.android.iviet.utils.FilterLog;

public class MainActivity extends BaseFragmentActivity implements ITop1FragmentListener {

	private static final String TAG = "MainActivity";
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	FilterLog log = new FilterLog(TAG);
	
	private final Handler mHandler = new Handler();
	private static final long DELAY_ON_DRAWER_CLICK = 250L;

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		MainFragment topFragment = new MainFragment();
		return topFragment;
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.main_ll_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.user_info, 0) {
			public void onDrawerOpened(View drawerView) {
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(getDrawerAdapter());
		mDrawerList.setOnItemClickListener(itemClickListener);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.actionbar_custom, null);
		ImageView imgChat = (ImageView) v.findViewById(R.id.img_chat);
		imgChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Chat click", Toast.LENGTH_SHORT).show();
			}
		});

		getActionBar().setCustomView(v);
	}

	/**
	 * item left-drawer click
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final FragmentManager fm = getSupportFragmentManager();
			if (parent.getId() == R.id.left_drawer) {
				final DrawerItem<?> item = (DrawerItem<?>) mDrawerList.getAdapter().getItem(position);
				if (item instanceof FragmentChangeDrawerItem) {
//					while (mFragmentTagStack.size() > 0) {
//						fm.popBackStackImmediate();
//					}
					Fragment f = ((FragmentChangeDrawerItem) item).getParam();
					if (f == null) {
						MainFragment fmain = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
						fmain.changeViewPager(0);
					} else {
						
						showFragment(f, false);
					}
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							mDrawerLayout.closeDrawer(mDrawerList);
							
						}
					}, DELAY_ON_DRAWER_CLICK);
				}
			}

		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onTop1AvatarClicked(Top1Fragment f, MainDto dto) {
		log.d("log>>>" + "onTop1AvatarClicked");

	}

	@Override
	public void onTop1ContentClicked(Top1Fragment f, MainDto dto) {
		log.d("log>>>" + "onTop1ContentClicked");

	}

	protected ListAdapter getDrawerAdapter() {
		final IVietApplication app = (IVietApplication) getApplication();
		return new DrawerAdapter(app.getDrawerItemGenerator().generateMain());
	}

	class DrawerAdapter extends ArrayAdapter<DrawerItem<?>> {
		public DrawerAdapter(List<DrawerItem<?>> objects) {
			super(MainActivity.this, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(getLayoutInflater(), convertView, parent);
		}
	}

}

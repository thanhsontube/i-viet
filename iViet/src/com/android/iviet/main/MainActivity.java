package com.android.iviet.main;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.iviet.IVietApplication;
import com.android.iviet.R;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.base.OnBackPressListener;
import com.android.iviet.base.WebViewFragment;
import com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem;
import com.android.iviet.main.drawer.FragmentChangeDrawerItem;
import com.android.iviet.main.dto.MainDto;
import com.android.iviet.main.fragment.FriendFragment;
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
	private FrameLayout mRightDrawer;

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
			
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				// close right drawer
				if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
					mDrawerLayout.closeDrawer(Gravity.RIGHT);
				}
				return super.onOptionsItemSelected(item);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		View footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.menu_list_drawer_footer, null);
		mDrawerList.addFooterView(footer);

		View convertView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.fragment_profile_drawer_item, null);
		mDrawerList.addHeaderView(convertView);

		ImageView imgProfile = (ImageView) convertView.findViewWithTag("img_button_profile");
		ImageView imgNew = (ImageView) convertView.findViewWithTag("img_button_new");
		ImageView imgAvatar = (ImageView) convertView.findViewWithTag("img_hexagon");
		TextView name = (TextView) convertView.findViewWithTag("drawer_profile_name");
		imgProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.imgProfile.setOnClickListener");
			}
		});

		imgNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.imgNew.setOnClickListener");
			}
		});
		name.setText("Taylor Swift");

		mDrawerList.setAdapter(getDrawerAdapter());
		mDrawerList.setOnItemClickListener(itemClickListener);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customViewActionBar = inflator.inflate(R.layout.actionbar_custom, null);
		ImageView imgChat = (ImageView) customViewActionBar.findViewById(R.id.img_chat);
		imgChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDrawerSelected(v.getId());
//				onop
			}
		});

		getActionBar().setCustomView(customViewActionBar);

		// Right Drawer
		mRightDrawer = (FrameLayout) findViewById(R.id.right_drawer);
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
					Fragment f = ((FragmentChangeDrawerItem) item).getParam();
					if (f instanceof MainFragment) {
						log.d("log>>>" + "f instanceof MainFragment");
						while (mFragmentTagStack.size() > 0) {
							fm.popBackStackImmediate();
							log.d("log>>>" + "fm.popBackStackImmediate()");
						}
						log.d("log>>>" + "f instanceof MainFragment:" + mFragmentTagStack.size());
						MainFragment fmain = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
						fmain.changeViewPager(0);
					} else {
						log.d("log>>>" + "showFragment");
						while (mFragmentTagStack.size() > 0) {
							fm.popBackStackImmediate();
							log.d("log>>>" + "fm.popBackStackImmediate()");
						}
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case android.R.id.home:
			log.d("log>>>" + "onOptionsItemSelected home:" + mFragmentTagStack.size());
			if (mFragmentTagStack.size() > 0) {
				Fragment f = getSupportFragmentManager().findFragmentByTag(mFragmentTagStack.peek());
				if (f instanceof WebViewFragment) {
					if (!((OnBackPressListener) f).onBackPress()) {
						log.d("log>>>" + "webview BACK");
						getSupportFragmentManager().popBackStackImmediate();

					}
				} else {
					getSupportFragmentManager().popBackStackImmediate();
				}
			}
			break;
			
		case R.id.img_chat:
			if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
				break;
			}
			if (mDrawerLayout.isDrawerVisible(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
			mDrawerLayout.openDrawer(Gravity.RIGHT);
			openFriendList();
			break;

		default:
			break;
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
		// WebViewFragment f1 =
		// WebViewFragment.newInstance("http://www.iviet.com/m/questions/q234?userToken=u10");
		String url = "file:///android_asset/q234.htm";
		// String url = "file:///android_asset/hello.html";
		WebViewFragment f1 = WebViewFragment.newInstance(url);
		showFragment(f1, false);

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

	@Override
	public void onBackStackChanged() {
		super.onBackStackChanged();
		if (mFragmentTagStack.size() > 0) {
			mDrawerToggle.setDrawerIndicatorEnabled(false);
		} else {
			mDrawerToggle.setDrawerIndicatorEnabled(true);
		}

	}

	private boolean onDrawerSelected(int id) {
		switch (id) {
//		case android.R.id.home:
//			if (mDrawerLayout.isDrawerVisible(Gravity.LEFT)) {
//				mDrawerLayout.closeDrawer(Gravity.LEFT);
//				break;
//			}
//			if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
//				mDrawerLayout.closeDrawer(Gravity.RIGHT);
//			}
//			mDrawerLayout.openDrawer(Gravity.LEFT);
//			break;
		case R.id.img_chat:
			if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
				break;
			}
			if (mDrawerLayout.isDrawerVisible(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
			mDrawerLayout.openDrawer(Gravity.RIGHT);
			openFriendList();
			break;
		default:
			break;
		}
		return true;
	}

	private void openFriendList() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.right_drawer, new FriendFragment());
		ft.commit();
		
	}
}

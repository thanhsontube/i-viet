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
import com.android.iviet.MsConst;
import com.android.iviet.R;
import com.android.iviet.about.AboutFragment.IAboutFragment;
import com.android.iviet.base.BaseFragmentActivity;
import com.android.iviet.base.OnBackPressListener;
import com.android.iviet.connection.BaseObject;
import com.android.iviet.friend.FriendFragment;
import com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem;
import com.android.iviet.main.drawer.FragmentChangeDrawerItem;
import com.android.iviet.main.fragment.MainFragment;
import com.android.iviet.main.fragment.Top1Fragment;
import com.android.iviet.main.fragment.Top1Fragment.ITop1FragmentListener;
import com.android.iviet.search.SearchDto;
import com.android.iviet.search.SearchFragment;
import com.android.iviet.search.SearchFragment.ISearchFragmentListener;
import com.android.iviet.user.EditProfileFragment;
import com.android.iviet.user.ProfileFragment;
import com.android.iviet.user.ProfileFragment.OnProfileFragmentInteractionListener;
import com.android.iviet.utils.ActionBarUtils;
import com.android.iviet.utils.BitmapUtils;
import com.android.iviet.utils.FilterLog;
import com.android.iviet.webview.DetailQuestionFragment;
import com.android.iviet.webview.DetailQuestionFragment.IDetailQuestionFragmentListener;
import com.android.iviet.webview.PolicyFragment;
import com.android.iviet.webview.RuleFragment;
import com.android.iviet.webview.WriteQuestionFragment;

public class MainActivity extends BaseFragmentActivity implements ITop1FragmentListener,
		MainFragment.IMainFragmentListener, IAboutFragment, ISearchFragmentListener, OnProfileFragmentInteractionListener, 
		IDetailQuestionFragmentListener{

	private static final String TAG = "MainActivity";
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	FilterLog log = new FilterLog(TAG);

	private final Handler mHandler = new Handler();
	private static final long DELAY_ON_DRAWER_CLICK = 250L;
	private FrameLayout mRightDrawer ;

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
		mDrawerList.addFooterView(footer, null, false);

		View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.fragment_profile_drawer_item, null);
		mDrawerList.addHeaderView(headerView, null, false);
		ImageView imgProfile = (ImageView) headerView.findViewWithTag("img_button_profile");
		ImageView imgNew = (ImageView) headerView.findViewWithTag("img_button_new");
		ImageView imgAvatar = (ImageView) headerView.findViewWithTag("img_hexagon");
		imgAvatar.setImageBitmap(BitmapUtils.maskBitmap(this, R.drawable.taylor_swift, R.drawable.hexagon_view2));
		TextView name = (TextView) headerView.findViewWithTag("drawer_profile_name");
		// TODO profile
		imgProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProfileFragment f = new ProfileFragment();
				showFragment(f, true);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mDrawerLayout.closeDrawer(mDrawerList);
					}
				}, DELAY_ON_DRAWER_CLICK);
			}
		});
		// TODO write question
		imgNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.imgNew.setOnClickListener");

				WriteQuestionFragment f = new WriteQuestionFragment();
				Bundle bundle = new Bundle();
				bundle.putString(MsConst.EXTRA_URL, "http://www.iviet.com/m/questions/post?userToken=u10");
				f.setArguments(bundle);
				showFragment(f, true);

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mDrawerLayout.closeDrawer(mDrawerList);
					}
				}, DELAY_ON_DRAWER_CLICK);

			}
		});
		name.setText("Taylor Swift");
		// getActionBar().setDisplayUseLogoEnabled(false);
		// View icon =
		// getActionBar().getCustomView().findViewById(android.R.id.home);
		// icon.setVisibility(View.INVISIBLE);

		mDrawerList.setAdapter(getDrawerAdapter());
		mDrawerList.setOnItemClickListener(itemClickListener);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		// getActionBar().setIcon(android.R.color.transparent);
		getActionBar().setIcon(R.drawable.setting_select);
		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customViewActionBar = inflator.inflate(R.layout.actionbar_custom, null);
		getActionBar().setCustomView(customViewActionBar);
		ImageView imgChat = (ImageView) customViewActionBar.findViewById(R.id.img_chat);
		imgChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDrawerSelected(v.getId());
			}
		});

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
				if (f instanceof DetailQuestionFragment || f instanceof WriteQuestionFragment) {
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
		// mDrawerToggle.syncState();
	}

	@Override
	public void onTop1AvatarClicked(Top1Fragment f, BaseObject dto) {
		log.d("log>>>" + "onTop1AvatarClicked");
		ProfileFragment f1 = new ProfileFragment();
		showFragment(f1, true);

	}

	@Override
	public void onTop1ContentClicked(Top1Fragment f, BaseObject dto) {
		log.d("log>>>" + "onTop1ContentClicked");
		// WebViewFragment f1 =
		// WebViewFragment.newInstance("http://www.iviet.com/m/questions/q234?userToken=u10");
		// String url = "file:///android_asset/hello.html";
		// WebViewFragment f1 = WebViewFragment.newInstance(url);
		DetailQuestionFragment f1 = new DetailQuestionFragment();
		Bundle bundle = new Bundle();
		bundle.putString(MsConst.EXTRA_URL, MsConst.URL_DETAIL_QUESTIONS);
		f1.setArguments(bundle);
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
			
			Fragment f = getSupportFragmentManager().findFragmentByTag(mFragmentTagStack.peek());
			if (f instanceof WriteQuestionFragment) {
				getActionBar().setDisplayHomeAsUpEnabled(false);
				getActionBar().setIcon(R.drawable.cancel);

			} else {
				
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setIcon(R.drawable.shape_icon);
			}

		} else {
			mDrawerToggle.setDrawerIndicatorEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(false);
			getActionBar().setIcon(R.drawable.setting_select);
		}

	}

	private boolean onDrawerSelected(int id) {
		switch (id) {
		// case android.R.id.home:
		// if (mDrawerLayout.isDrawerVisible(Gravity.LEFT)) {
		// mDrawerLayout.closeDrawer(Gravity.LEFT);
		// break;
		// }
		// if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
		// mDrawerLayout.closeDrawer(Gravity.RIGHT);
		// }
		// mDrawerLayout.openDrawer(Gravity.LEFT);
		// break;
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

	// main Fragment listener
	@Override
	public void onIMainFragmentStart(MainFragment f, int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMainFragmentPageSelected(MainFragment main, Fragment selected) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMainFragmentPageDeSelected(MainFragment main, Fragment selected) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMainFragmentMenuChatSelected(MainFragment main) {
		if (mDrawerLayout.isDrawerVisible(Gravity.RIGHT)) {
			mDrawerLayout.closeDrawer(Gravity.RIGHT);
			return;
		}
		if (mDrawerLayout.isDrawerVisible(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		openFriendList();

	}

	// TODO about fragment
	@Override
	public void onAboutFragmentTerm() {
		log.d("log>>>" + "onAboutFragmentTerm");
		Fragment f = new PolicyFragment();
		f.setArguments(getWebBundel(MsConst.URL_POLICY));
		showFragment(f, true);

	}

	@Override
	public void onAboutFragmentRuleOfAsk() {
		log.d("log>>>" + "onAboutFragmentRuleOfAsk");
		Fragment f = new RuleFragment();
		f.setArguments(getWebBundel(MsConst.URL_RULE));
		showFragment(f, true);
	}

	private Bundle getWebBundel(String url) {
		Bundle bundle = new Bundle();
		bundle.putString(MsConst.EXTRA_URL, url);
		return bundle;
	}

	@Override
	public void onISearchFragmentListenerItemClicked(SearchFragment fragment, SearchDto entity) {
		Fragment f = new DetailQuestionFragment();
		f.setArguments(getWebBundel(MsConst.URL_DETAIL_QUESTIONS));
		showFragment(f, true);
	}

	@Override
	public void onProfileFragmentInteraction(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case R.id.profile_btn_edit:
			Fragment f = new EditProfileFragment();
			showFragment(f, true);
			break;

		default:
			break;
		}
	}

	@Override
    public void onDetailQuestionFragmentAnswer() {
	    
		DetailQuestionFragment f = (DetailQuestionFragment) getSupportFragmentManager().findFragmentByTag(mFragmentTagStack.peek());
		f.setShowSendMenu(true);
		f.iAddImage = View.VISIBLE;
		f.iShowTop = View.GONE;
		f.getActivity().invalidateOptionsMenu();
    }

	@Override
    public void onDetailQuestionFragmentBack() {
		DetailQuestionFragment f = (DetailQuestionFragment) getSupportFragmentManager().findFragmentByTag(mFragmentTagStack.peek());
		f.setShowSendMenu(false);
		f.iAddImage = View.GONE;
		f.iShowTop = View.VISIBLE;
		f.getActivity().invalidateOptionsMenu();
	    
    }
}

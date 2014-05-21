package com.android.iviet.main.drawer;

import java.util.ArrayList;
import java.util.List;

import com.android.iviet.R;
import com.android.iviet.about.AboutFragment;
import com.android.iviet.base.WebViewFragment;
import com.android.iviet.help.HelpFragment;
import com.android.iviet.main.fragment.MainFragment;
import com.android.iviet.newfeed.NewFeedFragment;
import com.android.iviet.test.Test1Fragment;
import com.android.iviet.test.Test2Fragment;

import android.content.Context;

public class MainDrawerItemGenerator implements
		DrawerItemGenerator<com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem<?>> {

	private final Context context;

	public MainDrawerItemGenerator(Context c) {
		this.context = c.getApplicationContext();
	}

	@Override
	public List<DrawerItem<?>> generateMain() {
		final List<DrawerItem<?>> list = new ArrayList<DrawerItem<?>>();
		 list.add(new FragmentProfileDrawerItem(0,
		 context.getText(R.string.home), "profile", "profile", null));
		list.add(new FragmentChangeDrawerItem(R.drawable.home, context.getText(R.string.home), "home", "home",
				new MainFragment()));
		list.add(new FragmentChangeDrawerItem(R.drawable.notification, context.getText(R.string.notification),
				"notification", "notification", new NewFeedFragment()));
		list.add(new FragmentChangeDrawerItem(R.drawable.help, context.getText(R.string.help), "help", "help",
				WebViewFragment.newInstance("http://vnexpress.net")));
		list.add(new FragmentChangeDrawerItem(R.drawable.about_us, context.getText(R.string.about), "about", "about",
				new AboutFragment()));
		list.add(new FragmentChangeDrawerItem(R.drawable.search, context.getText(R.string.search), "search", "search",
				new MainFragment()));
		list.add(new FragmentChangeDrawerItem(R.drawable.log_out, context.getText(R.string.logout), "logout", "logout",
				new MainFragment()));
		// list.add(new FragmentChangeDrawerItem(0,
		// context.getText(R.string.sologan), "sologan", "sologan",
		// new MainFragment()));
		return list;
	}

}

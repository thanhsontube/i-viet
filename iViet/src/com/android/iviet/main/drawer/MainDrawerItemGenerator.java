package com.android.iviet.main.drawer;

import java.util.ArrayList;
import java.util.List;

import com.android.iviet.R;
import com.android.iviet.main.fragment.MainFragment;
import com.android.iviet.test.Test1Fragment;

import android.content.Context;


public class MainDrawerItemGenerator implements DrawerItemGenerator<com.android.iviet.main.drawer.DrawerItemGenerator.DrawerItem<?>> {
	
	private final Context context;
	public MainDrawerItemGenerator(Context c) {
		this.context = c.getApplicationContext();
	}

	@Override
    public List<DrawerItem<?>> generateMain() {
		final List<DrawerItem<?>> list = new ArrayList<DrawerItem<?>>();
		list.add(new FragmentProfileDrawerItem(0, context.getText(R.string.home), "profile", "profile", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.home), "home", "home", new MainFragment()));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.notification), "notification", "notification", new Test1Fragment()));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.help), "help", "help", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.about), "about", "about", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.search), "search", "search", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.logout), "logout", "logout", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.sologan), "sologan", "sologan", null));
	    return list;
    }

	
}

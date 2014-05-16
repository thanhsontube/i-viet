package com.android.iviet.main.drawer;

import java.util.ArrayList;
import java.util.List;

import com.android.iviet.R;

import android.content.Context;

public class MainDrawerItemGenerator implements DrawerItemGenerator<FragmentChangeDrawerItem> {
	
	private final Context context;
	public MainDrawerItemGenerator(Context c) {
		this.context = c.getApplicationContext();
	}

	@Override
    public List<FragmentChangeDrawerItem> generateMain() {
		List<FragmentChangeDrawerItem> list = new ArrayList<FragmentChangeDrawerItem>();
		
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.home), "home", "home", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.notification), "notification", "notification", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.help), "help", "help", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.about), "about", "about", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.search), "search", "search", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.logout), "logout", "logout", null));
		list.add(new FragmentChangeDrawerItem(0, context.getText(R.string.sologan), "sologan", "sologan", null));
	    return list;
    }

	
}

package com.android.iviet.base;

import com.android.iviet.about.AboutIParent;
import com.android.iviet.connection.ContentManager;
import com.android.iviet.connection.PathIAccess;
import com.android.iviet.main.drawer.MainDrawerItemGenerator;

public interface Factory {
	ContentManager getContentManager();

	PathIAccess getPathAccess();

	MainDrawerItemGenerator getDrawerItemGenerator();
	
	AboutIParent getAboutParent();
}

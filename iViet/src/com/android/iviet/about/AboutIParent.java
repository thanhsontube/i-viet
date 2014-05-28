package com.android.iviet.about;

import java.util.List;

import android.view.View;

public interface AboutIParent {
	List<AboutItem> generate();
	
	View getHeader();

	boolean isUpdate();
}

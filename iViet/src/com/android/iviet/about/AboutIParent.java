package com.android.iviet.about;

import java.util.List;

import android.content.Context;
import android.view.View;

public interface AboutIParent {
	List<AboutItem> generate();
	
	View getHeader();

	boolean isUpdate();
}

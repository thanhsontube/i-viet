package com.android.iviet.main.fragment;

import com.android.iviet.MsConst;


public class Top1Fragment extends BaseTopFragment{

	@Override
    public int initType() {
	    return MsConst.TYPE_NEWEST;
    }

	@Override
    protected String generateTitle() {
	    return null;
    }
	
}

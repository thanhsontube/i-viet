package com.android.iviet.json;

import java.util.List;

public class DataObject {
	String cursor;
	List<BaseObject> listdata;
	String more;
	

	public DataObject() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCursor() {
		return cursor;
	}


	public void setCursor(String cursor) {
		this.cursor = cursor;
	}


	public List<BaseObject> getListdata() {
		return listdata;
	}


	public void setListdata(List<BaseObject> listdata) {
		this.listdata = listdata;
	}


	public String getMore() {
		return more;
	}


	public void setMore(String more) {
		this.more = more;
	}

	

}

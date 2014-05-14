package com.android.iviet.main.dto;

import java.util.List;

import com.android.iviet.json.BaseObject;
import com.android.iviet.json.DataObject;

public class MainDto {
	String cursor;
	List<BaseObject> listdata;
	String more;
	

	public MainDto() {
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

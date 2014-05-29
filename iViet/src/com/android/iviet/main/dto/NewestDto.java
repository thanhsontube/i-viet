package com.android.iviet.main.dto;

import java.util.List;

import com.android.iviet.json.BaseObject;

public class NewestDto {
	private String cursor;
	private String more;
	private List<BaseObject> list;

	public List<BaseObject> getList() {
		return list;
	}

	public void setList(List<BaseObject> list) {
		this.list = list;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}
	
	
}

package com.android.iviet.json;

import java.util.List;

import com.android.iviet.main.dto.MainDto;

public class DataObject {
	String cursor;
	List<BaseObject> newest;
	String more;
	List<BaseObject> featured;
	List<BaseObject> yours;

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

	public List<BaseObject> getNewest() {
		return newest;
	}

	public void setNewest(List<BaseObject> newest) {
		this.newest = newest;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public List<BaseObject> getFeatured() {
		return featured;
	}

	public void setFeatured(List<BaseObject> featured) {
		this.featured = featured;
	}

	public List<BaseObject> getYours() {
		return yours;
	}

	public void setYours(List<BaseObject> yours) {
		this.yours = yours;
	}

}

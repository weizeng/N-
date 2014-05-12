package com.nhaowan.mobile.base.bean;

public class InnerMenu {

	int id;
	String title;
	boolean isChecked;

	public InnerMenu(int id, String title, boolean isChecked) {
		super();
		this.id = id;
		this.title = title;
		this.isChecked = isChecked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}

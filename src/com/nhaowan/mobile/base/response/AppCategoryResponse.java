package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.leo.utils.Element;
import com.leo.utils.ElementList;
import com.nhaowan.mobile.base.bean.AppCategoryBean;

public class AppCategoryResponse {
	@Element(value = "ret")
	private int ret;

	@Element(value = "msg")
	private String msg;

	@ElementList(value = "list")
	private ArrayList<AppCategoryBean> list = new ArrayList<AppCategoryBean>();

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<AppCategoryBean> getList() {
		return list;
	}

	public void setList(ArrayList<AppCategoryBean> list) {
		this.list = list;
	}

}

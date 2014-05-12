package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.haha.frame.utils.Element;
import com.haha.frame.utils.ElementList;
import com.haha.frame.widget.ListResult;
import com.nhaowan.mobile.base.bean.NewsBean;

public class NewsBusinessResponse extends ListResult {
	@Element(value = "ret")
	private int ret;

	@Element(value = "page_count")
	private int pageCount;

	@Element(value = "msg")
	private String msg;

	@ElementList(value = "list", checkSuper = true)
	private ArrayList<NewsBean> list = new ArrayList<NewsBean>();

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

	public ArrayList<NewsBean> getList() {
		return list;
	}

	public void setList(ArrayList<NewsBean> list) {
		this.list = list;
	}
	
	@Override
	public  int getTotalPage(){
		return pageCount;
	}

	@Override
	public int getDataSize4EachRequest() {
		return list.size();
	}
	
	
}

package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.leo.utils.Element;
import com.leo.utils.ElementList;
import com.leo.widget.ListResult;
import com.nhaowan.mobile.base.bean.MyCommentBean;

public class MyCommentListResponse extends ListResult{
	@Element(value = "ret")
	private int ret;

	@Element(value = "page_count")
	private int pageCount;

	@Element(value = "msg")
	private String msg;

	@ElementList(value = "list")
	private ArrayList<MyCommentBean> list = new ArrayList<MyCommentBean>();

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<MyCommentBean> getList() {
		return list;
	}

	public void setList(ArrayList<MyCommentBean> list) {
		this.list = list;
	}

	@Override
	public int getTotalPage() {
		return pageCount;
	}

	@Override
	public int getDataSize4EachRequest() {
		return list == null ? 0 : list.size();
	}

}

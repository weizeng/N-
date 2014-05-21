package com.nhaowan.mobile.base.response;

import java.util.ArrayList;

import com.leo.utils.Element;
import com.leo.utils.ElementList;
import com.leo.widget.ListResult;
import com.nhaowan.mobile.base.bean.CommentTopBean;

public class CommentTopResponse extends ListResult {
	@Element(value = "ret")
	private int ret;

	@Element(value = "msg")
	private String msg;

	@Element(value = "page_count")
	private int pageCount;

	@ElementList(value = "list", checkSuper = true)
	private ArrayList<CommentTopBean> list = new ArrayList<CommentTopBean>();

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

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

	public ArrayList<CommentTopBean> getList() {
		return list;
	}

	public void setList(ArrayList<CommentTopBean> list) {
		this.list = list;
	}

	@Override
	public int getDataSize4EachRequest() {
		return list.size();
	}

	@Override
	public int getTotalPage() {
		return getPageCount();
	}

}
